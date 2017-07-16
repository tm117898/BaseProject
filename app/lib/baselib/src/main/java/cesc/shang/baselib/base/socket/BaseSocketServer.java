package cesc.shang.baselib.base.socket;

import android.os.Handler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.support.utils.UtilsManager;
import cesc.shang.utilslib.utils.debug.LogUtils;
import cesc.shang.utilslib.utils.util.MapSetUtils;
import cesc.shang.utilslib.utils.util.ThreadUtils;

/**
 * Created by shanghaolongteng on 2016/8/2.
 */
public abstract class BaseSocketServer {
    protected BaseApplication mApp;
    protected LogUtils mLog;

    private ServerSocket mServer = null;
    public static final String ACCEPT_THREAD_NAME = "BaseSocketServer-AcceptThread";
    private Handler mAcceptWork = null;

    private Map<Object, BaseSocketClient> mRegisterClients = Collections.synchronizedMap(new HashMap<Object, BaseSocketClient>());
    private Set<BaseSocketClient> mUnregisterClients = Collections.synchronizedSet(new HashSet<BaseSocketClient>());

    public BaseSocketServer(BaseApplication app) {
        mApp = app;
        mLog = getUtilsManager().getLogUtils(BaseSocketServer.class.getSimpleName());
        mAcceptWork = getThreadUtils().getHandlerThread(ACCEPT_THREAD_NAME);
        mAcceptWork.post(new Runnable() {
            @Override
            public void run() {
                init();
            }
        });
    }

    private ThreadUtils getThreadUtils() {
        return getUtilsManager().getThreadUtils();
    }

    private UtilsManager getUtilsManager() {
        return mApp.getUtilsManager();
    }

    protected void init() {
        try {
            mServer = new ServerSocket(getServerPort());
        } catch (IOException e) {
            e.printStackTrace();
            initServerSocketFail();
        }

        if (mServer != null) {
            try {
                for (; !mServer.isClosed(); ) {
                    Socket socket = mServer.accept();
                    mLog.i("init , 有连接上来");
                    BaseSocketClient client = initClient(socket, mListener);
                    mUnregisterClients.add(client);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private SocketClientListener mListener = new SocketClientListener() {
        @Override
        public void register(Object key, BaseSocketClient client) {
            mLog.i("SocketClientListener , register , key : " + key);
            if (mUnregisterClients.contains(client)) {
                mUnregisterClients.remove(client);
            }
            mRegisterClients.put(key, client);
        }

        @Override
        public void breakLine(final BaseSocketClient client) {
            MapSetUtils utils = getMapSetUtils();
            if (mRegisterClients.containsValue(client)) {
                utils.traversalMap(mRegisterClients, new MapSetUtils.MapTraversalCallBack<Object, BaseSocketClient>() {
                    @Override
                    public boolean findObject(Object key, BaseSocketClient value) {
                        if (value == client) {
                            mRegisterClients.remove(key);
                            return true;
                        }
                        return false;
                    }
                });
            }

            if (mUnregisterClients.contains(client)) {
                utils.traversalSet(mUnregisterClients, new MapSetUtils.SetTraversalCallBack<BaseSocketClient>() {
                    @Override
                    public boolean findObject(BaseSocketClient value) {
                        if (value == client) {
                            mUnregisterClients.remove(value);
                            return true;
                        }
                        return false;
                    }
                });
            }
        }
    };

    private MapSetUtils getMapSetUtils() {
        return getUtilsManager().getMapSetUtils();
    }

    public abstract int getServerPort();

    public abstract void initServerSocketFail();

    public abstract void acceptServerSocketFail();

    public abstract BaseSocketClient initClient(Socket socket, SocketClientListener listener);

    public void destroy() {
        mAcceptWork.removeCallbacksAndMessages(null);

        try {
            mServer.close();
            mLog.i("destroy , mServer.close()");
        } catch (IOException e) {
            e.printStackTrace();
        }

        mAcceptWork.post(new Runnable() {
            @Override
            public void run() {
                MapSetUtils utils = getMapSetUtils();
                utils.traversalMap(mRegisterClients, new MapSetUtils.MapTraversalCallBack<Object, BaseSocketClient>() {
                    @Override
                    public boolean findObject(Object key, BaseSocketClient value) {
                        value.destroy();
                        if (mRegisterClients.containsKey(key)) {
                            mRegisterClients.remove(key);
                        }
                        return false;
                    }
                });

                utils.traversalSet(mUnregisterClients, new MapSetUtils.SetTraversalCallBack<BaseSocketClient>() {
                    @Override
                    public boolean findObject(BaseSocketClient value) {
                        value.destroy();
                        if (mUnregisterClients.contains(value)) {
                            mUnregisterClients.remove(value);
                        }
                        return false;
                    }
                });
                mLog.i("destroy , mServer.close()");
                getThreadUtils().quitHandlerThread(mAcceptWork);
            }
        });
    }

    public <T extends BaseSocketSendEntity> void sendMessageByAll(T t) {
        final String message = t.convertString();
        MapSetUtils utils = getMapSetUtils();
        utils.traversalMap(mRegisterClients, new MapSetUtils.MapTraversalCallBack<Object, BaseSocketClient>() {
            @Override
            public boolean findObject(Object key, BaseSocketClient value) {
                value.sendMessage(message);
                return false;
            }
        });

        utils.traversalSet(mUnregisterClients, new MapSetUtils.SetTraversalCallBack<BaseSocketClient>() {
            @Override
            public boolean findObject(BaseSocketClient value) {
                value.sendMessage(message);
                return false;
            }
        });
    }

    public <T extends BaseSocketSendEntity> void sendMessageByKey(Object key, T t) {
        if (mRegisterClients.containsKey(key)) {
            BaseSocketClient client = mRegisterClients.get(key);
            String message = t.convertString();
            client.sendMessage(message);
        }
    }
}
