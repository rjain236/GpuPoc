/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.xlloop.script;

import org.apache.bsf.BSFManager;
import org.boris.xlloop.IFunction;
import org.boris.xlloop.IFunctionContext;
import org.boris.xlloop.RequestException;
import org.boris.xlloop.SessionContext;
import org.boris.xlloop.util.XLoperObjectConverter;
import org.boris.xlloop.xloper.XLArray;
import org.boris.xlloop.xloper.XLoper;

public class BSFScript implements IFunction
{
    private XLoperObjectConverter converter = new XLoperObjectConverter();
    private String lang;
    private String source;
    private String name;

    public BSFScript(String lang, String source, String name) {
        this.lang = lang;
        this.source = source;
        this.name = name;
    }

    public static Class[] createArgHints(XLoper[] args) {
        Class[] hints = new Class[args.length];
        for (int i = 0; i < hints.length; i++) {
            XLoper v = args[i];
            if (v instanceof XLArray) {
                XLArray c = (XLArray) v;
                if (c.columns > 1) {
                    hints[i] = Object[][].class;
                } else {
                    hints[i] = Object[].class;
                }
            } else {
                hints[i] = Object.class;
            }
        }
        return hints;
    }

    public XLoper execute(IFunctionContext context, XLoper[] args) throws RequestException {
        try {
            Object[] a = converter.convert(args, createArgHints(args), new SessionContext(context));
            BSFManager manager = new BSFManager();
            manager.declareBean("context", a, IFunctionContext.class);
            manager.declareBean("args", a, Object[].class);
            Object res = manager.eval(lang, name, 1, 1, source);
            return converter.createFrom(res, new SessionContext(context));
        } catch (Throwable e) {
            throw new RequestException(e);
        }
    }
}
