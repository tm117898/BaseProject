package cesc.shang.demo.examples.ipc.socket;

import java.net.Socket;

import cesc.shang.baselib.base.service.BaseService;
import cesc.shang.baselib.base.socket.BaseSocketClient;
import cesc.shang.baselib.base.socket.BaseSocketServer;
import cesc.shang.baselib.base.socket.BaseSocketServerClient;
import cesc.shang.baselib.base.socket.SocketClientListener;

/**
 * Created by shanghaolongteng on 2016/8/3.
 */
public class SocketService extends BaseService {
    private SocketEntity mEntity = new SocketEntity();
    private BaseSocketServer mServer = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mServer = new BaseSocketServer(getApp()) {
            @Override
            public int getServerPort() {
                return 7898;
            }

            @Override
            public void initServerSocketFail() {
                mLog.e("initServerSocketFail");
            }

            @Override
            public void acceptServerSocketFail() {
                mLog.e("acceptServerSocketFail");
            }

            @Override
            public BaseSocketClient initClient(Socket socket, SocketClientListener listener) {
                return new BaseSocketServerClient(getApp(), socket, listener) {
                    @Override
                    public Object convertRegisterMessage(String message) {
                        mLog.i("convertRegisterMessage : message : ", message);
                        return message;
                    }

                    @Override
                    public void onReceiveMessage(String message) {
                        mLog.i("onReceiveMessage , message : ", message);
                        if ("all".equals(message)) {
                            mServer.sendMessageByAll(SocketService.this.mEntity);
                        } else {
                            mServer.sendMessageByKey(message, SocketService.this.mEntity);
                        }
                    }
                };
            }
        };

        mLog.i("服务端已启动");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mServer != null)
            mServer.destroy();

        mLog.i("服务端已停止");
    }
}
