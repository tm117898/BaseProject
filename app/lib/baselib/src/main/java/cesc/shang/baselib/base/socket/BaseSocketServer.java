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
import cesc.shang.baselib.support.manager.UtilsManager;
import cesc.shang.utilslib.utils.debug.LogUtils;
import cesc.shang.utilslib.utils.util.MapSetUtils;
import cesc.shang.utilslib.utils.util.ThreadUtils;

/**
 * Created by shanghaolongteng on 2016/8/2.
 */
public abstract class BaseSocketServer {
    public static final String ACCEPT_THREAD_NAME = "BaseSocketServer-AcceptThread";

    protected BaseApplication mApp;
    protected LogUtils mLog;
    private ServerSocket mServer = null;
    private Handler mAcceptWork = null;

    private Map<Object, BaseSocketClient> mRegisterClients =
            Collections.synchronizedMap(new HashMap<Object, BaseSocketClient>());
    private Set<BaseSocketClient> mUnregisterClients =
            Collections.synchronizedSet(new HashSet<BaseSocketClient>());

    public BaseSocketServer(BaseApplication app) {
        mApp = app;
        mLog = getUtilsManager().getLogUtils(BaseSocketServer.class.getSimpleName());
        initAcceptThread();
    }

    /**
     * 初始化server的连接等待线程
     */
    private void initAcceptThread() {
        mAcceptWork = getThreadUtils().getHandlerThread(ACCEPT_THREAD_NAME);
        mAcceptWork.post(new Runnable() {
            @Override
            public void run() {
                init();
            }
        });
    }

    /**
     * 创建ServerSocket，并循环等待客户端连接
     */
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
                acceptServerSocketFail();
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

    private ThreadUtils getThreadUtils() {
        return getUtilsManager().getThreadUtils();
    }

    private UtilsManager getUtilsManager() {
        return mApp.getUtilsManager();
    }

    /**
     * 返回Server端口号
     *
     * @return 端口号
     */
    public abstract int getServerPort();

    /**
     * 创建ServerSocket失败
     */
    public abstract void initServerSocketFail();

    /**
     * 等待客户端连接时出现异常
     */
    public abstract void acceptServerSocketFail();

    /**
     * 与客户端建立连接,返回server端的client
     *
     * @param socket   连接
     * @param listener 数据回调listener
     * @return {@link BaseSocketServer}
     */
    public abstract BaseSocketClient initClient(Socket socket, SocketClientListener listener);

    /**
     * 断开连接并释放资源
     */
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

    /**
     * 向所有客户端发送信息
     *
     * @param t   要发送的数据
     * @param <T> 要发送数据类型
     */
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

    /**
     * 向指定客户端发送信息
     *
     * @param key 指定客户端的id
     * @param t   要发送的数据
     * @param <T> 要发送数据类型
     */
    public <T extends BaseSocketSendEntity> void sendMessageByKey(Object key, T t) {
        if (mRegisterClients.containsKey(key)) {
            BaseSocketClient client = mRegisterClients.get(key);
            String message = t.convertString();
            client.sendMessage(message);
        }
    }
}
