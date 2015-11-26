/*
 * @author Avinash Johnson
 */
package com.finmechanics.fmcom.xlloopspring;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.boris.xlloop.IFunction;
import org.boris.xlloop.IFunctionContext;
import org.boris.xlloop.RequestException;
import org.boris.xlloop.xloper.XLMissing;
import org.boris.xlloop.xloper.XLNil;
import org.boris.xlloop.xloper.XLoper;

/**
 * The Class OverloadedSpringMethod.
 */
public class OverloadedSpringMethod implements IFunction
{
    
    /** The methods. */
    private List methods = new ArrayList();

    /**
     * Adds the.
     *
     * @param m the m
     */
    public void add(SpringInstanceMethod m) {
        this.methods.add(m);
    }

    /* (non-Javadoc)
     * @see org.boris.xlloop.IFunction#execute(org.boris.xlloop.IFunctionContext, org.boris.xlloop.xloper.XLoper[])
     */
    public XLoper execute(IFunctionContext context, XLoper[] args) throws RequestException {
        int lastArg = args.length - 1;
        for (; lastArg >= 0; lastArg--) {
            if (!(args[lastArg] instanceof XLNil || args[lastArg] instanceof XLMissing)) {
                break;
            }
        }

        SpringInstanceMethod matched = null;
        double matchPercent = 0;

        for (Iterator i = methods.iterator(); i.hasNext();) {
        	SpringInstanceMethod m = (SpringInstanceMethod) i.next();
            double mc = m.calcMatchPercent(args, lastArg);
            if (mc > 0 && mc > matchPercent) {
                matched = m;
                matchPercent = mc;
            }
        }

        if (matched != null)
            return matched.execute(context, args);

        throw new RequestException("#Invalid args");
    }

    /**
     * Gets the first method.
     *
     * @return the first method
     */
    public SpringInstanceMethod getFirstMethod() {
        return (SpringInstanceMethod) methods.get(0);
    }
}

