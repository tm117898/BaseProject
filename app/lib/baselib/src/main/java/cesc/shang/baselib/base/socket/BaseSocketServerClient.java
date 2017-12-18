package cesc.shang.baselib.base.socket;

import java.net.Socket;

import cesc.shang.baselib.base.application.BaseApplication;

/**
 * Created by shanghaolongteng on 2016/8/2.
 *
 * @param <T> 对应客户端的key
 */
public abstract class BaseSocketServerClient<T> extends BaseSocketClient<T> {
    private BaseSocketServerClient(BaseApplication app, String targetHostName, int tagHostPort, T t,
                                   SocketClientListener listener) {
        super(app, targetHostName, tagHostPort, t, listener);
    }

    public BaseSocketServerClient(BaseApplication app, final Socket socket, SocketClientListener listener) {
        super(app, listener);
        mWriteWork.post(new Runnable() {
            @Override
            public void run() {
                init(socket);
            }
        });
    }

    @Override
    protected void sendRegisterMessage() {
        //super.sendRegisterMessage();//do not nothing
    }

    @Override
    public String getRegisterMessage(T t) {
        return null;
    }

    @Override
    protected void receiveMessage(String message) {
        if (!disposeRegisterMessage(message)) {
            super.receiveMessage(message);
        }
    }

    /**
     * server 接收到消息，先判断是否是客户端注册类消息，不是的话再下发到{@link #onReceiveMessage(String)}
     *
     * @param message 消息内容
     * @return 是否处理该消息
     */
    protected boolean disposeRegisterMessage(String message) {
        if (message.startsWith(REGISTER_MESSAGE_START_TAG) && message.endsWith(REGISTER_MESSAGE_END_TAG)) {
            if (mListener != null) {
                int startIndex = REGISTER_MESSAGE_START_TAG.length();
                int endIndex = message.lastIndexOf(REGISTER_MESSAGE_END_TAG);
                T t = convertRegisterMessage(message.substring(startIndex, endIndex));
                mListener.register(t, this);
            }
            return true;
        }
        return false;
    }

    /**
     * 收到注册类消息，将这转换成对应客户端的key
     *
     * @param message 消息内容
     * @param <T>     转换后的客户端的key
     * @return 客户端的key
     */
    public abstract <T> T convertRegisterMessage(String message);
}
