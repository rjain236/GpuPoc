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

import java.io.IOException;
import java.io.Reader;

import org.boris.xlloop.IFunction;
import org.boris.xlloop.IFunctionContext;
import org.boris.xlloop.RequestException;
import org.boris.xlloop.SessionContext;
import org.boris.xlloop.util.XLoperObjectConverter;
import org.boris.xlloop.xloper.XLoper;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class JavaScriptFactory implements ScriptFactory
{
    private static XLoperObjectConverter converter = new XLoperObjectConverter();

    public IFunction create(Reader r) throws IOException {
        return new JavaScriptFunction(Context.enter().compileReader(r, null, 0, null), converter);
    }

    private static class JavaScriptFunction implements IFunction
    {
        private Script script;
        private XLoperObjectConverter converter;

        public JavaScriptFunction(Script script, XLoperObjectConverter converter) {
            this.script = script;
            this.converter = converter;
        }

        public XLoper execute(IFunctionContext context, XLoper[] args) throws RequestException {
            Context ctx = Context.enter();
            Object[] oargs = converter.convert(args, BSFScript.createArgHints(args), new SessionContext(context));
            ScriptableObject so = ctx.initStandardObjects();
            Scriptable argsObj = ctx.newArray(so, oargs);
            so.defineProperty("args", argsObj, ScriptableObject.DONTENUM);
            try {
                return converter.createFrom(script.exec(ctx, so),new SessionContext(context));
            } catch (Throwable t) {
                throw new RequestException(t.getMessage());
            }
        }
    }
}
