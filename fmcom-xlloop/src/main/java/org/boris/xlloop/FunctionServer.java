/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.xlloop;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.boris.xlloop.codec.BinaryRequestProtocol;
import org.boris.xlloop.util.XLList;
import org.boris.xlloop.xloper.XLBool;
import org.boris.xlloop.xloper.XLError;
import org.boris.xlloop.xloper.XLInt;
import org.boris.xlloop.xloper.XLSRef;
import org.boris.xlloop.xloper.XLString;
import org.boris.xlloop.xloper.XLoper;

public class FunctionServer implements IBuiltinFunctions
{
    protected int port;
    protected IFunctionHandler handler;
    protected ServerSocket socket;
    protected IFunctionServerListener listener;

    public FunctionServer() {
        this(5454);
    }

    public FunctionServer(int port) {
        this.port = port;
    }

    public int getPort() {
        return socket == null ? port : socket.getLocalPort();
    }

    public FunctionServer(int port, IFunctionHandler f) {
        this.port = port;
        this.handler = f;
    }

    public void setFunctionHandler(IFunctionHandler h) {
        this.handler = h;
    }

    public IFunctionHandler getFunctionHandler() {
        return this.handler;
    }

    public void setListener(IFunctionServerListener listener) {
        this.listener = listener;
    }

    public void stop() throws IOException {
        if (socket != null)
            socket.close();
        socket = null;
    }

    public void start() {
        if (socket != null)
            return;

        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    FunctionServer.this.run();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.setName("XLLoop Function Server");
        t.setDaemon(true);
        t.start();
    }

    public void run() throws IOException {
        if (socket == null)
            socket = new ServerSocket(port);

        while (true) {
            HandlerThread ht = new HandlerThread(handler, socket.accept());
            ht.start();
            if (listener != null)
                listener.connectionCreated(ht);
        }
    }

    public static void handleRequest(IFunctionHandler handler, IRequestProtocol protocol, Socket socket, Session session) {
        try {
            XLoper nameOrVersion = protocol.receive(socket);
            XLString name = null;
            IFunctionContext context = null;
            if (nameOrVersion.type == XLoper.xlTypeInt) {
                int version = ((XLInt) nameOrVersion).w;
                if (version == 20) {
                    XLBool b = (XLBool) protocol.receive(socket);
                    if (b.bool) {
                        XLoper caller = protocol.receive(socket);
                        XLoper sheetName = protocol.receive(socket);
                        XLSRef cref = null;
                        if (caller instanceof XLSRef)
                            cref = (XLSRef) caller;
                        String namestr = null;
                        if (sheetName instanceof XLString)
                            namestr = ((XLString) sheetName).str;
                        context = new FunctionContext(handler, session, cref, namestr);
                    }
                } else {
                    protocol.send(socket, new XLString("#Unknown protocol version"));
                    socket.close();
                    return;
                }
                name = (XLString) protocol.receive(socket);
            } else {
                name = (XLString) nameOrVersion;
            }
            XLInt argCount = (XLInt) protocol.receive(socket);
            XLoper[] args = new XLoper[argCount.w];
            for (int i = 0; i < argCount.w; i++) {
                args[i] = protocol.receive(socket);
            }
            if (!session.init && name.str.equals(INITIALIZE)) {
                initializeSession(session, args);
            }

            if (session.init && context == null)
                context = new FunctionContext(handler, session, null, null);

            XLoper res = handler.execute(context, name.str, args);
            if (res == null)
                res = XLError.NULL;
            protocol.send(socket, res);
        } catch (RequestException e) {
            try {
                protocol.send(socket, new XLString(e.getMessage()));
            } catch (IOException ex) {
                System.err.println(e.getMessage());
                try {
                    socket.close();
                } catch (IOException iex) {
                }
            }
        } catch (Throwable e) {
//            System.err.println(e.getMessage());
//            try {
//                socket.close();
//            } catch (IOException ex) {
//            }
        	try {
                protocol.send(socket, new XLString(e.getMessage()));
            } catch (IOException ex) {
                System.err.println(e.getMessage());
                try {
                    socket.close();
                } catch (IOException iex) {
                }
            }
        }
    }

    private static void initializeSession(Session session, XLoper[] args) {
        session.init = true;
        XLList l = new XLList(args);
        int size = l.size();
        switch (size) {
        default:
        case 3:
            session.key = l.getString(2);
        case 2:
            session.host = l.getString(1);
        case 1:
            session.user = l.getString(0);
        case 0:
            break;
        }
    }

    public static class HandlerThread extends Thread
    {
        private Socket socket;
        private IRequestProtocol protocol = new BinaryRequestProtocol();
        private IFunctionHandler handler;
        private Session session = new Session();

        public HandlerThread(IFunctionHandler handler, Socket socket) {
            super("XLLoop Function Server Handler");
            this.handler = handler;
            this.socket = socket;
        }

        public void run() {
            try {
                protocol.initialise(socket);
            } catch (IOException e) {
            }
            while (!socket.isClosed()) {
                handleRequest(handler, protocol, socket, session);
            }
            handler.clear(new SessionContext(session.host, session.user));
            System.out.println("Closed for host: "+session.host+" user : "+session.user);
        }

        public void close() {
            try {
                socket.getInputStream().close();
                socket.getOutputStream().close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class FunctionContext implements IFunctionContext
    {
        private IFunctionHandler handler;
        private Session session;
        private XLSRef caller;
        private String sheetName;

        public FunctionContext(IFunctionHandler handler, Session session, XLSRef caller, String sheetName) {
            this.handler = handler;
            this.session = session;
            this.caller = caller;
            this.sheetName = sheetName;
        }

        public String getHost() {
            return session.host;
        }

        public String getUser() {
            return session.user;
        }

        public String getUserKey() {
            return session.key;
        }

        public XLSRef getCaller() {
            return caller;
        }

        public String getSheetName() {
            return sheetName;
        }

        public XLoper execute(String name, XLoper[] args) throws RequestException {
            return handler.execute(this, name, args);
        }
    }

    private static class Session
    {
        private String user;
        private String host;
        private String key;
        private boolean init;
    }
}
