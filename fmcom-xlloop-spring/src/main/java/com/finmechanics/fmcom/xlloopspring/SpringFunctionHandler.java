/*
 * @author Avinash Johnson
 */
package com.finmechanics.fmcom.xlloopspring;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

/**
 * The Class SpringFunctionHandler.
 */
@Component
@Scope("singleton")
public class SpringFunctionHandler implements ApplicationContextAware, IFunctionHandler, FunctionProvider{

	/** The information. */
	private Map information = new HashMap();
	 
 	/** The methods. */
 	private Map methods = new HashMap();
 	
 	private Map<String,Integer> methodsCount = new HashMap<>();
	 
 	/** The converter. */
 	@Autowired
 	private ConverterSpringBean converter;
	    
	/** The application context. */
	ApplicationContext applicationContext;
	
	public ConverterSpringBean getConverter(){
		return converter;
	}
	
	public void setConverter(ConverterSpringBean converter){
		this.converter = converter;
	}
	
	
	public SpringFunctionHandler() {
	}
	
	 /* (non-Javadoc)
 	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
 	 */
 	@Override
	    public void setApplicationContext(ApplicationContext applicationContext)
	            throws BeansException {
	        this.applicationContext= applicationContext;
	    }

	/* (non-Javadoc)
	 * @see org.boris.xlloop.handler.FunctionProvider#getFunctions()
	 */
 	
 	public void setConverter(){
 		
 	}
 	
	@Override
	public FunctionInformation[] getFunctions() {
		ArrayList functions = new ArrayList();
        try {
			for (Iterator i = methods.keySet().iterator(); i.hasNext();) {
			    String key = (String) i.next();
			    FunctionInformation fi = (FunctionInformation) information.get(key);
			    if (fi != null) {
			        functions.add(fi);
			        continue;
			    }
			    IFunction f = (IFunction) methods.get(key);
			    fi = new FunctionInformation(key);
			    if (f instanceof SpringInstanceMethod) {
			    	SpringInstanceMethod im = null;
			    	try {
			            im = (SpringInstanceMethod) f;
			            int size = im.args.length;
			            String[] paramsNames = new String[size];
			            for(int index = 0;index<size;index++)
			            	paramsNames[index] = "Arg"+index; 
			            for (int j = 0; j < paramsNames.length; j++) {
			                fi.addArgument(paramsNames[j], im.args[j].getSimpleName());
			            }

//			            ParameterNameExtractor pne = new ParameterNameExtractor(im.clazz);
//			            String[] names = pne.getParameterNames(im.method);
//			            for (int j = 0; j < names.length; j++) {
//			                fi.addArgument(names[j], im.args[j].getSimpleName());
//			            }
			        } catch (Exception e) {
			        	System.err.println("Error in processing key " +  key + ": " + (im== null? "im null": im.clazz.getName()));
			        	e.printStackTrace();
			        }
			    }
//            else if (f instanceof OverloadedMethod) {
//                try {
//                    OverloadedMethod om = (OverloadedMethod) f;
//                    SpringInstanceMethod im = om.getFirstMethod();
//                    ParameterNameExtractor pne = new ParameterNameExtractor(im.clazz);
//                    String[] names = pne.getParameterNames(im.method);
//                    for (int j = 0; j < names.length; j++) {
//                        fi.addArgument(names[j], im.args[j].getSimpleName());
//                    }
//                } catch (Exception e) {
//                }
//            }
			    functions.add(fi);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return (FunctionInformation[]) functions.toArray(new FunctionInformation[0]);
    }


	/* (non-Javadoc)
	 * @see org.boris.xlloop.IFunctionHandler#execute(org.boris.xlloop.IFunctionContext, java.lang.String, org.boris.xlloop.xloper.XLoper[])
	 */
	@Override
	public XLoper execute(IFunctionContext context, String name, XLoper[] args) throws RequestException {
		IFunction f = (IFunction) methods.get(name);
        if (f == null) {
            throw new RequestException("#Unknown method: " + name);
        }
        return f.execute(context, args);
	}

	/* (non-Javadoc)
	 * @see org.boris.xlloop.IFunctionHandler#hasFunction(java.lang.String)
	 */
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

			Class c = null;
			try {
				c = Class.forName(beanClass);
			} catch (ClassNotFoundException e) {
				//TODO(jonnie) suppress exception
				e.printStackTrace();
				return;
			}
			Class[] interfaces = c.getInterfaces();
			List<String> beanNames = new ArrayList<>();
			for(int i=0;i<interfaces.length;i++){
				String[] beanName = SpringUtils.getBeanNames(applicationContext, interfaces[i]);
				for(int j=0;j<beanName.length;j++)
					beanNames.add(beanName[j]);
			}
			if(beanNames != null && beanNames.size() > 0){
			for (int j = 0; j < beanNames.size(); j++) {
				String beanName = beanNames.get(j);
				Method[] m = c.getMethods();
		        for (int i = 0; i < m.length; i++) {
		            if (Modifier.isPublic(m[i].getModifiers()) && objectMethodsMap.get(m[i].getName()) == null) {
		            	System.out.println("Public method :" + m[i].getName() + m.toString());
		                if (namespace == null) {
		                    addMethod(c,  beanName, m[i].getName() , m[i]);
		                } else {
		                    addMethod(c, namespace + beanName,  m[i].getName(), m[i]);
		                }
		            }
		        }

			}
				
	
			}
	        	
	}

/**
 * Adds the method.
 *
 * @param cls the cls
 * @param beanName the bean name
 * @param methodName the method name
 * @param m the m
 */
@SuppressWarnings("unchecked")
public void addMethod(Class cls, String beanName, String methodName,  Method m) {
   
	IFunction f = (IFunction) methods.get(beanName);
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
        methods.put(beanName + "_" +  methodName+append, new SpringInstanceMethod(cls, applicationContext, beanName, methodName,  m, converter.getInstance()));
        System.out.println(beanName + "_" +  methodName+append);
    
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
		converter.getInstance().clearRegistry(sessionContext);
	}


	
}
