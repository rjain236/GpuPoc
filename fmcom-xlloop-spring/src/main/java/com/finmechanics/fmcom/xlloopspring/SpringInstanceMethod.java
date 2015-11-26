/*
 * @author Avinash Johnson
 */
package com.finmechanics.fmcom.xlloopspring;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.boris.xlloop.IFunction;
import org.boris.xlloop.IFunctionContext;
import org.boris.xlloop.RequestException;
import org.boris.xlloop.SessionContext;
import org.boris.xlloop.util.XLoperObjectConverter;
import org.boris.xlloop.xloper.XLBool;
import org.boris.xlloop.xloper.XLError;
import org.boris.xlloop.xloper.XLInt;
import org.boris.xlloop.xloper.XLNum;
import org.boris.xlloop.xloper.XLoper;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;

/**
 * The Class SpringInstanceMethod.
 */
public class SpringInstanceMethod implements IFunction
{
    
    /** The clazz. */
    Class clazz;
    //Object instance;
    /** The application context. */
    ApplicationContext applicationContext;
    
    /** The bean name. */
    String beanName ;
    
    /** The method name. */
    String methodName;
    
    /** The method. */
    Method method;
    
    /** The converter. */
    XLoperObjectConverter converter;
    
    /** The args. */
    Class[] args;

    /**
     * Instantiates a new spring instance method.
     *
     * @param clazz the clazz
     * @param applicationContext the application context
     * @param beanName the bean name
     * @param methodName the method name
     * @param method the method
     * @param converter the converter
     */
    public SpringInstanceMethod(Class clazz, ApplicationContext applicationContext,  String beanName, String methodName,  Method method, XLoperObjectConverter converter) {
        this.clazz = clazz;
        //this.instance = instance;
        this.applicationContext=applicationContext;
        this.beanName=beanName;
        this.methodName=methodName;
        this.method = method;
        this.converter = converter;
        this.args = method.getParameterTypes();
    }

    /* (non-Javadoc)
     * @see org.boris.xlloop.IFunction#execute(org.boris.xlloop.IFunctionContext, org.boris.xlloop.xloper.XLoper[])
     */
    public XLoper execute(IFunctionContext context, XLoper[] args) throws RequestException {
    	SessionContext sessionContext = new SessionContext(context);
        return converter.createFrom(execute(converter.convert(args, this.args, sessionContext)), sessionContext);
    }

    /**
     * Matches args.
     *
     * @param args the args
     * @param lastArg the last arg
     * @return true, if successful
     * @throws RequestException the request exception
     */
    boolean matchesArgs(XLoper[] args, int lastArg) throws RequestException {
        if (lastArg >= this.args.length) {
            return false;
        }

        return args != null;
    }

    /**
     * Calc match percent.
     *
     * @param args the args
     * @param lastArg the last arg
     * @return the double
     * @throws RequestException the request exception
     */
    double calcMatchPercent(XLoper[] args, int lastArg) throws RequestException {
        if (lastArg >= this.args.length) {
            return 0;
        }

        double calc = 0;
        int i = 0;
        for (; i < args.length && i < this.args.length; i++) {
            calc += calcMatchPercent(args[i], this.args[i]);
        }

        return calc / i;
    }

    /**
     * Calc match percent.
     *
     * @param arg the arg
     * @param c the c
     * @return the double
     * @throws RequestException the request exception
     */
    double calcMatchPercent(XLoper arg, Class c) throws RequestException {
        if (c == null)
            return 100;
        switch (arg.type) {
        case XLoper.xlTypeBool:
            if (c == Boolean.class || c == XLBool.class)
                return 100;
            else if (c.isAssignableFrom(Number.class))
                return 50;
            break;
        case XLoper.xlTypeErr:
            if (c == XLError.class)
                return 100;
            break;
        case XLoper.xlTypeInt:
            if (c == Integer.class || c == XLInt.class)
                return 100;
            else if (c.isAssignableFrom(Number.class))
                return 50;
            break;
        case XLoper.xlTypeMulti:
            break;
        case XLoper.xlTypeNum:
            if (c == double.class || c == Double.class || c == XLNum.class)
                return 100;
            else if (c.isAssignableFrom(Number.class) || c == int.class || c == long.class || c == float.class)
                return 50;
            break;
        case XLoper.xlTypeStr:
            // Design bug - we don't know at this point
            return 100;
        }

        return 0;
    }
    
    @SuppressWarnings({"unchecked"})
    protected <T> T getTargetObject(Object proxy, Class<T> targetClass) throws Exception {
      if (AopUtils.isJdkDynamicProxy(proxy)) {
        return (T) ((Advised)proxy).getTargetSource().getTarget();
      } else {
        return (T) proxy; 
      }
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
        	Object instance = getTargetObject(applicationContext.getBean(beanName),clazz);
            Object result = method.invoke(instance, (Object[]) args);
            return result;
        } catch (IllegalArgumentException e) {
            throw new RequestException(e);
        } catch (IllegalAccessException e) {
            throw new RequestException(e);
        } catch (InvocationTargetException e) {
            throw new RequestException(e.getTargetException());
        } catch (BeansException e) {
        	throw new RequestException(e);
        } catch (Exception e) {
        	throw new RequestException(e);
        }
    }
}
