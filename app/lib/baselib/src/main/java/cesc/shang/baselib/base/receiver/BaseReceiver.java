package cesc.shang.baselib.base.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.support.BaseSupport;
import cesc.shang.baselib.support.controller.AppController;
import cesc.shang.baselib.support.manager.AppManager;
import cesc.shang.baselib.support.utils.UtilsManager;
import cesc.shang.utilslib.utils.debug.LogUtils;

/**
 * Created by shanghaolongteng on 2017/7/15.
 */

public class BaseReceiver extends BroadcastReceiver implements BaseSupport {
    protected LogUtils mLog;

    @Override
    public void onReceive(Context context, Intent intent) {
        mLog = getUtilsManager(context).getLogUtils(this.getClass().getSimpleName());
        mLog.i("onReceive , intent : ", intent);
    }

    @Override
    public BaseApplication getApp(Context context) {
        return (BaseApplication) context.getApplicationContext();
    }

    @Override
    public AppController getAppController(Context context) {
        return getApp(context).getAppController();
    }

    @Override
    public AppManager getAppManager(Context context) {
        return getApp(context).getAppManager();
    }

    @Override
    public UtilsManager getUtilsManager(Context context) {
        return getApp(context).getUtilsManager();
    }
}
