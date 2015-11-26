package com.finmechanics.fmcom.xlloopspring;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.boris.xlloop.IFunction;
import org.boris.xlloop.IFunctionContext;
import org.boris.xlloop.IFunctionHandler;
import org.boris.xlloop.RequestException;
import org.boris.xlloop.SessionContext;
import org.boris.xlloop.handler.FunctionInformation;
import org.boris.xlloop.handler.FunctionProvider;
import org.boris.xlloop.reflect.ParameterNameExtractor;
import org.boris.xlloop.util.XLoperObjectConverter;
import org.boris.xlloop.xloper.XLoper;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.finmechanics.fmcom.annotations.xlserver.ExposeConstructors;
/**
 * @author rjain236
 *
 */
public class NonSpringFunctionHandler implements ApplicationContextAware, IFunctionHandler, FunctionProvider{
	
	/** The information. */
	private Map<String,FunctionInformation> information = new HashMap<>();
	 
 	/** The methods. */
 	private Map<String, IFunction> methods = new HashMap<>();
 	
 	private Map<String, Integer> methodsCount = new HashMap<>();
	 
 	/** The converter. */
 	private XLoperObjectConverter converter;
	    
	/** The application context. */
	ApplicationContext applicationContext;
	
	public NonSpringFunctionHandler(XLoperObjectConverter converter) {
		this.converter = converter;
	}
	
 	@Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext= applicationContext;
    }

	
	@Override
	public FunctionInformation[] getFunctions() {
		ArrayList<FunctionInformation> functions = new ArrayList<FunctionInformation>();
        try {
			for (Iterator<String> i = methods.keySet().iterator(); i.hasNext();) {
			    String key = i.next();
			    FunctionInformation fi = information.get(key);
			    if (fi != null) {
			        functions.add(fi);
			        continue;
			    }
			    IFunction f = methods.get(key);
			    fi = new FunctionInformation(key);
			    if (f instanceof NonSpringInstanceMethod) {
			    	NonSpringInstanceMethod im = (NonSpringInstanceMethod)f;
			    	try {
			            int size = im.args.length;
			            String[] paramsNames = new String[size];
			            int j = 0;
			            if(!im.isStatic){
			            	paramsNames[j] = "Obj";
			            	j++;
			            }
			            for(int index = 0;index<(size-j);index++)
			            	paramsNames[index+j] = "Arg"+index; 
			            for (j = 0; j < paramsNames.length; j++) {
			                fi.addArgument(paramsNames[j], im.args[j].getSimpleName());
			            }
			        } catch (Exception e) {
			        	System.err.println("Error in processing key " +  key + ": " + (im== null? "im null": im.clazz.getName()));
			        	e.printStackTrace();
			        }
			    }
			    else if (f instanceof NonSpringInstanceConstructor) {
			    	NonSpringInstanceConstructor im = (NonSpringInstanceConstructor)f;
			    	try {
			            int size = im.args.length;
			            String[] paramsNames = new String[size];
			            for(int index = 0;index<size;index++)
			            	paramsNames[index] = "Arg"+index; 
			            for (int j = 0; j < paramsNames.length; j++) {
			                fi.addArgument(paramsNames[j], im.args[j].getSimpleName());
			            }
			        } catch (Exception e) {
			        	System.err.println("Error in processing key " +  key + ": " + (im== null? "im null": im.clazz.getName()));
			        	e.printStackTrace();
			        }
			    }

			    information.put(key, fi);
			    functions.add(fi);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return (FunctionInformation[]) functions.toArray(new FunctionInformation[0]);

	}

	@Override
	public XLoper execute(IFunctionContext context, String name, XLoper[] args)
			throws RequestException {
		IFunction f = (IFunction) methods.get(name);
        if (f == null) {
            throw new RequestException("#Unknown method: " + name);
        }
        return f.execute(context, args);
	}

	@Override
	public boolean hasFunction(String name) {
        return methods.containsKey(name);
	}

	
	/**
	 * Adds the bean.
	 *
	 * @param namespace the namespace
	 * @param beanClass the bean class
	 */
	public void addBean(String namespace, String beanClass){
		Class<?> c = null;
		try {
			c = Class.forName(beanClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}
    	String beanName = c.getSimpleName();
		Method[] m = c.getMethods();
        for (int i = 0; i < m.length; i++) {
            if (Modifier.isPublic(m[i].getModifiers()) && objectMethodsMap.get(m[i].getName()) == null) {
            	System.out.println("Public method :" + m[i].getName() + m.toString());
                boolean isStatic = Modifier.isStatic(m[i].getModifiers());
            	if (namespace == null) {
                    addMethod(c,  beanName, m[i].getName() , m[i],isStatic);
                } else {
                    addMethod(c, namespace + beanName,  m[i].getName(), m[i],isStatic);
                }
            }
        }
        
        if (c.isAnnotationPresent(ExposeConstructors.class)) {
	        Constructor<?>[] constructors = c.getConstructors();
	        for(int i=0; i < constructors.length;i++ ){
	        	if(Modifier.isPublic(constructors[i].getModifiers())){
	        		addConstructor(c, beanName, constructors[i]);
	        	}
	        }
        }
	}
	
	/**
	 * Adds the constructor.
	 *
	 * @param cls the cls
	 * @param beanName the bean name
	 * @param c the constructor
	 */
	public void addConstructor(Class<?> cls, String beanName,  Constructor<?> c) {
		String id = beanName + "_of";
		String append = "";
		if(methodsCount.containsKey(id)){
			int prev = methodsCount.get(id);
			int next = prev+1;
			methodsCount.put(id, next);
			append = append+next;
		}else{
			methodsCount.put(id, 1);
		}
        methods.put(id+append, new NonSpringInstanceConstructor(cls, id+append,  c, converter));
        System.out.println(beanName + "_" +  id+append);
	}

	/**
	 * Adds the method.
	 *
	 * @param cls the cls
	 * @param beanName the bean name
	 * @param methodName the method name
	 * @param m the m
	 */
	public void addMethod(Class<?> cls, String beanName, String methodName,  Method m, boolean isStatic) {
		String id = beanName + "_" +  methodName;
		String append = "";
		if(methodsCount.containsKey(id)){
			int prev = methodsCount.get(id);
			int next = prev+1;
			methodsCount.put(id, next);
			append = append+next;
		}else{
			methodsCount.put(id, 1);
		}
        methods.put(id+append, new NonSpringInstanceMethod(cls, methodName,  m, converter,isStatic));
        System.out.println(beanName + "_" +  methodName);
	}

	/** The Constant objectMethodsMap. */
	private static final Map<String, String> objectMethodsMap = new HashMap<String, String>() {
    
    private static final long serialVersionUID = -434202494028073714L;

    {
      put("equals", "single");
      put("getClass", "single");
      put("hashCode", "single");
      put("notify", "single");
      put("notifyAll", "single");
      put("toString", "single");
      put("wait", "overloaded");
     Collections.unmodifiableMap(this);
    }
  };

	@Override
	public void clear(SessionContext sessionContext) {
		converter.clearRegistry(sessionContext);
	}



}
