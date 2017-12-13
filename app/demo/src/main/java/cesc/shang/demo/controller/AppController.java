package cesc.shang.demo.controller;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.support.manager.ControllerManager;
import cesc.shang.demo.examples.ipc.aidl.AIDLController;
import cesc.shang.demo.examples.ipc.binderconnectionpool.BinderController;
import cesc.shang.demo.examples.ipc.socket.SocketController;
import cesc.shang.demo.examples.main.MainController;
import cesc.shang.demo.examples.okhttp.OkHttpController;

/**
 * Created by shanghaolongteng on 2017/7/16.
 */

public class AppController extends ControllerManager {
    private MainController mMain;
    private BinderController mBinder;
    private AIDLController mAidl;
    private SocketController mSocket;
    private OkHttpController mOkHttp;

    public AppController(BaseApplication app) {
        super(app);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        destroyManager(mMain);
        mMain = null;
        destroyManager(mBinder);
        mBinder = null;
        destroyManager(mAidl);
        mAidl = null;
        destroyManager(mSocket);
        mSocket = null;
        destroyManager(mOkHttp);
        mOkHttp = null;
    }

    public MainController getMainController() {
        if (mMain == null) {
            mMain = new MainController(getApp());
        }
        return mMain;
    }

    public BinderController getBinderController() {
        if (mBinder == null) {
            mBinder = new BinderController(getApp());
        }
        return mBinder;
    }

    public AIDLController getAIDLController() {
        if (mAidl == null) {
            mAidl = new AIDLController(getApp());
        }
        return mAidl;
    }

    public SocketController getSocketController() {
        if (mSocket == null) {
            mSocket = new SocketController(getApp());
        }
        return mSocket;
    }

    public OkHttpController getOkHttpController() {
        if (mOkHttp == null) {
            mOkHttp = new OkHttpController(getApp());
        }
        return mOkHttp;
    }
}
