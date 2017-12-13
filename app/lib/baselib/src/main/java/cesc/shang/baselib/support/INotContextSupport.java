package cesc.shang.baselib.support;

import android.content.Context;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.support.manager.ControllerManager;
import cesc.shang.baselib.support.manager.HandlerManager;
import cesc.shang.baselib.support.manager.UtilsManager;

/**
 * Created by shanghaolongteng on 2017/7/15.
 */

public interface INotContextSupport {
    BaseApplication getApp(Context context);

    ControllerManager getControllerManager(Context context);

    HandlerManager getHandlerManager(Context context);

    UtilsManager getUtilsManager(Context context);
}
