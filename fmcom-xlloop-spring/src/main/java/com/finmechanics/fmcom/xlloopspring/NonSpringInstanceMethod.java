package com.finmechanics.fmcom.xlloopspring;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.commons.lang.ArrayUtils;
import org.boris.xlloop.IFunction;
import org.boris.xlloop.IFunctionContext;
import org.boris.xlloop.RequestException;
import org.boris.xlloop.SessionContext;
import org.boris.xlloop.util.XLoperObjectConverter;
import org.boris.xlloop.xloper.XLoper;

/**
 * @author rjain236
 *
 */
public class NonSpringInstanceMethod implements IFunction
{
    
    /** The clazz. */
    Class<?> clazz;

    /** The method name. */
    String methodName;
    
    /** The method. */
    Method method;
    
    /** The converter. */
    XLoperObjectConverter converter;
    
    /** The args. */
    Class<?>[] args;
    
    /**Static or not**/
    boolean isStatic;

    /**
     * Instantiates a new spring instance method.
     *
     * @param clazz the clazz
     * @param applicationContext the application context
     * @param method the method
     * @param converter the converter
     */
    public NonSpringInstanceMethod(Class<?> clazz, String methodName,  Method method, XLoperObjectConverter converter,
    		boolean isStatic) {
        this.clazz = clazz;
        this.methodName=methodName;
        this.method = method;
        this.converter = converter;
        this.isStatic = isStatic;
    	this.args = (isStatic)?method.getParameterTypes():(Class<?>[]) ArrayUtils.addAll(new Class<?>[]{clazz}, method.getParameterTypes());;
    }

    public XLoper execute(IFunctionContext context, XLoper[] args) throws RequestException {
    	SessionContext sessionContext = new SessionContext(context);
        return converter.createFrom(execute(converter.convert(args, this.args, sessionContext)),sessionContext);
    }

    /**
     * Execute.
     *
     * @param args the args
     * @return the object
     * @throws RequestException the request exception
     */
    Object execute(Object[] args) throws RequestException {
        try {
        	if(isStatic)
        		return method.invoke(null, args);
        	Object instance = args[0];
        	Object[] modifiedArgs = Arrays.copyOfRange(args, 1, args.length);
            return method.invoke(instance, modifiedArgs);
        } catch (IllegalArgumentException e) {
            throw new RequestException(e);
        } catch (IllegalAccessException e) {
            throw new RequestException(e);
        } catch (InvocationTargetException e) {
            throw new RequestException(e.getTargetException());
        }
    }
}
