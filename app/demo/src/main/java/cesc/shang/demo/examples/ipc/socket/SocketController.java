package cesc.shang.demo.examples.ipc.socket;

import android.content.Intent;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.base.socket.BaseSocketClient;
import cesc.shang.baselib.base.socket.SocketClientListener;
import cesc.shang.baselib.support.BaseContextSupport;
import cesc.shang.baselib.support.BaseManager;
import cesc.shang.baselib.support.callback.IGetDataSuccessCallBack;

/**
 * Created by shanghaolongteng on 2017/7/16.
 */

public class SocketController extends BaseManager {
    private BaseSocketClient<String> mUser1 = null;
    private BaseSocketClient<String> mUser2 = null;

    public SocketController(BaseApplication app) {
        super(app);
    }

    public void onActivityCreate(BaseContextSupport support) {
        support.getContext().startService(new Intent(support.getContext(), SocketService.class));
    }

    public void onActivityDestroy(BaseContextSupport support) {
        support.getContext().stopService(new Intent(support.getContext(), SocketService.class));
        mUser1 = mUser2 = null;
    }

    public void user1OnLine(final IGetDataSuccessCallBack<String> callBack) {
        mUser1 = new BaseSocketClient<String>(mApp, "localhost", 7898, "1", new SocketClientListener() {
            @Override
            public void register(Object key, BaseSocketClient client) {
            }

            @Override
            public void breakLine(BaseSocketClient client) {
                mUser1 = null;
            }
        }) {
            @Override
            public String getRegisterMessage(String integer) {
                return integer.toString();
            }

            @Override
            public void onReceiveMessage(String message) {
                callBack.onSuccess("用户1收到消息");
            }
        };
    }

    public void user1OffLine() {
        if (mUser1 != null) {
            mUser1.destroy();
            mUser1 = null;
        }
    }

    public void user2OnLine(final IGetDataSuccessCallBack<String> callBack) {
        mUser2 = new BaseSocketClient<String>(mApp, "localhost", 7898, "2", new SocketClientListener() {
            @Override
            public void register(Object key, BaseSocketClient client) {

            }

            @Override
            public void breakLine(BaseSocketClient client) {
                mUser2 = null;
            }
        }) {
            @Override
            public String getRegisterMessage(String integer) {
                return integer.toString();
            }

            @Override
            public void onReceiveMessage(String message) {
                callBack.onSuccess("用户2收到消息");
            }
        };
    }

    public void user2OffLine() {
        if (mUser2 != null) {
            mUser2.destroy();
            mUser2 = null;
        }
    }

    public void user1sendMessage(String s) {
        sendMessage(mUser1, s);
    }

    public void user2sendMessage(String s) {
        sendMessage(mUser2, s);
    }

    private void sendMessage(BaseSocketClient client, String s) {
        if (client != null) {
            client.sendMessage(s);
        }
    }

}
