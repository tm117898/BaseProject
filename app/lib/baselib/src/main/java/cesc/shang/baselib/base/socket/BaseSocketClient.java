package cesc.shang.baselib.base.socket;

import android.os.Handler;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.utilslib.utils.debug.LogUtils;
import cesc.shang.utilslib.utils.util.ThreadUtils;

/**
 * Created by shanghaolongteng on 2016/8/2.
 */
public abstract class BaseSocketClient<T> {
    protected BaseApplication mApp;
    protected LogUtils mLog;
    public static final String REGISTER_MESSAGE_START_TAG = "REGISTER_MESSAGE_START_TAG";
    public static final String REGISTER_MESSAGE_END_TAG = "REGISTER_MESSAGE_END_TAG";

    private static final String READ_THREAD_NAME = "BaseSocketServer-ReadThread", WRITE_THREAD_NAME = "BaseSocketServer-WriteThread";
    private Socket mSocket = null;
    protected Handler mReadWork = null, mWriteWork = null;

    protected SocketClientListener mListener = null;
    protected T mEntity = null;

    private BufferedReader mRead = null;
    private PrintWriter mWrite = null;

    public BaseSocketClient(BaseApplication app, SocketClientListener listener) {
        mApp = app;
        mLog = app.getUtilsManager().getLogUtils(this.getClass().getSimpleName());
        mListener = listener;
        initWriteWork();
    }

    public BaseSocketClient(BaseApplication app, T t, SocketClientListener listener) {
        this(app, listener);
        mEntity = t;
    }

    public BaseSocketClient(BaseApplication app, final String targetHostName, final int tagHostPort, T t, SocketClientListener listener) {
        this(app, t, listener);

        mWriteWork.post(new Runnable() {
            @Override
            public void run() {
                init(targetHostName, tagHostPort);
            }
        });
    }

    private void initWriteWork() {
        mWriteWork = getThreadUtils().getHandlerThread(WRITE_THREAD_NAME);
    }

    protected void init(String targetHostName, int tagHostPort) {
        try {
            Socket socket = new Socket(targetHostName, tagHostPort);

            init(socket);
        } catch (IOException e) {
            e.printStackTrace();
            breakLine();
        }
    }

    private void breakLine() {
        if (mListener != null)
            mListener.breakLine(this);
        destroy();
    }

    protected void init(Socket socket) {
        mSocket = socket;
        mReadWork = getThreadUtils().getHandlerThread(READ_THREAD_NAME);

        try {
            mWrite = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            mRead = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            readServerMessage();
            sendRegisterMessage();
        } catch (IOException e) {
            e.printStackTrace();
            breakLine();
        }
    }

    private ThreadUtils getThreadUtils() {
        return mApp.getUtilsManager().getThreadUtils();
    }

    protected void sendRegisterMessage() {
        StringBuffer buffer = new StringBuffer(REGISTER_MESSAGE_START_TAG);
        buffer.append(getRegisterMessage(mEntity));
        buffer.append(REGISTER_MESSAGE_END_TAG);
        sendMessage(buffer.toString());
        buffer.setLength(0);
    }

    public abstract String getRegisterMessage(T t);

    private void readServerMessage() {
        mReadWork.post(new Runnable() {
            @Override
            public void run() {
                for (; !mSocket.isClosed(); ) {
                    try {
                        mLog.i("readServerMessage , start read!");
                        String message = mRead.readLine();
                        if (TextUtils.isEmpty(message)) {
                            breakLine();
                            break;
                        } else {
                            receiveMessage(message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                breakLine();
            }
        });
    }

    protected void receiveMessage(String message) {
        onReceiveMessage(message);
    }

    public abstract void onReceiveMessage(String message);

    public void sendMessage(final String message) {
        if (mSocket.isClosed())
            return;

        mWriteWork.post(new Runnable() {
            @Override
            public void run() {
                mWrite.println(message);
                mWrite.flush();
            }
        });
    }

    public void destroy() {
        if (mSocket.isClosed())
            return;

        mListener = null;

        try {
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mWriteWork.removeCallbacksAndMessages(null);
        mWriteWork.post(new Runnable() {
            @Override
            public void run() {
                mLog.i("mWrite.close();");
                mWrite.close();
                getThreadUtils().quitHandlerThread(mWriteWork);
            }
        });

        mReadWork.removeCallbacksAndMessages(null);
        mReadWork.post(new Runnable() {
            @Override
            public void run() {
                mLog.i("mRead.close();");
                try {
                    mRead.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                getThreadUtils().quitHandlerThread(mReadWork);
            }
        });
    }
}
