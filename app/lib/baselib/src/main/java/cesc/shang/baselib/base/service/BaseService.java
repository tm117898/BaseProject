package cesc.shang.baselib.base.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.support.manager.ControllerManager;
import cesc.shang.baselib.support.manager.HandlerManager;
import cesc.shang.baselib.support.manager.UtilsManager;
import cesc.shang.utilslib.utils.debug.LogUtils;

/**
 * Created by shanghaolongteng on 2017/7/15.
 */

public class BaseService extends Service implements cesc.shang.baselib.support.IContextSupport {
    protected LogUtils mLog;

    @Override
    public void onCreate() {
        super.onCreate();
        mLog = getUtilsManager().getLogUtils(this.getClass().getSimpleName());
        mLog.i("create");
    }

    @Override
    public IBinder onBind(Intent intent) {
        mLog.i("onBind");
        return null;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        mLog.i("onBind , intent : ", intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mLog.i("onStartCommand , intent : ", intent, " , flags : ", flags, " , startId : ", startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mLog.i("onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLog.i("destroy");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mLog.i("onLowMemory");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        mLog.i("onTrimMemory , level : ", level);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public BaseApplication getApp() {
        return (BaseApplication) this.getApplication();
    }

    @Override
    public ControllerManager getControllerManager() {
        return getApp().getControllerManager();
    }

    @Override
    public HandlerManager getHandlerManager() {
        return getApp().getHandlerManager();
    }

    @Override
    public UtilsManager getUtilsManager() {
        return getApp().getUtilsManager();
    }
}
