package cesc.shang.demo.controller;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.support.controller.ControllerManager;
import cesc.shang.demo.examples.ipc.aidl.AIDLController;
import cesc.shang.demo.examples.ipc.binderconnectionpool.BinderController;
import cesc.shang.demo.examples.ipc.socket.SocketController;
import cesc.shang.demo.examples.main.MainController;

/**
 * Created by shanghaolongteng on 2017/7/16.
 */

public class AppController extends ControllerManager {
    private MainController mMain;
    private BinderController mBinder;
    private AIDLController mAidl;
    private SocketController mSocket;

    public AppController(BaseApplication app) {
        super(app);
    }

    public MainController getMainController() {
        if (mMain == null) {
            mMain = new MainController(mApp);
        }
        return mMain;
    }

    public BinderController getBinderController() {
        if (mBinder == null) {
            mBinder = new BinderController(mApp);
        }
        return mBinder;
    }

    public AIDLController getAIDLController() {
        if (mAidl == null) {
            mAidl = new AIDLController(mApp);
        }
        return mAidl;
    }

    public SocketController getSocketController() {
        if (mSocket == null) {
            mSocket = new SocketController(mApp);
        }
        return mSocket;
    }
}
