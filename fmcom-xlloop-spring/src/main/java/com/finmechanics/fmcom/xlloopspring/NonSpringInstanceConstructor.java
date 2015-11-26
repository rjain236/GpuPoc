package com.finmechanics.fmcom.xlloopspring;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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
public class NonSpringInstanceConstructor implements IFunction
{
    
    /** The clazz. */
    Class<?> clazz;

    /** The constructor name. */
    String constructorName;
    
    /** The Constructor. */
    Constructor<?> constructor;
    
    /** The converter. */
    XLoperObjectConverter converter;
    
    /** The args. */
    Class<?>[] args;
    
    /**
     * Instantiates a new spring instance method.
     *
     * @param clazz the clazz
     * @param applicationContext the application context
     * @param method the method
     * @param converter the converter
     */
    public NonSpringInstanceConstructor(Class<?> clazz, String constructorName,  Constructor<?> constructor, XLoperObjectConverter converter) {
        this.clazz = clazz;
        this.constructor = constructor;
        this.converter = converter;
    	this.args = constructor.getParameterTypes();
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
            return constructor.newInstance(args);
        } catch (IllegalArgumentException e) {
            throw new RequestException(e);
        } catch (IllegalAccessException e) {
            throw new RequestException(e);
        } catch (InvocationTargetException e) {
            throw new RequestException(e.getTargetException());
        } catch (InstantiationException e) {
        	throw new RequestException(e);
		}
    }
}
