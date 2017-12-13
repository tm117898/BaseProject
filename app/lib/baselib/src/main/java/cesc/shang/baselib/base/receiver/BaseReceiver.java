package cesc.shang.baselib.base.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.support.INotContextSupport;
import cesc.shang.baselib.support.manager.ControllerManager;
import cesc.shang.baselib.support.manager.HandlerManager;
import cesc.shang.baselib.support.manager.UtilsManager;
import cesc.shang.utilslib.utils.debug.LogUtils;

/**
 * Created by shanghaolongteng on 2017/7/15.
 */

public class BaseReceiver extends BroadcastReceiver implements INotContextSupport {
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
    public ControllerManager getControllerManager(Context context) {
        return getApp(context).getControllerManager();
    }

    @Override
    public HandlerManager getHandlerManager(Context context) {
        return getApp(context).getHandlerManager();
    }

    @Override
    public UtilsManager getUtilsManager(Context context) {
        return getApp(context).getUtilsManager();
    }
}
