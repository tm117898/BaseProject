package cesc.shang.baselib.support.context;

import android.content.Context;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.support.manager.ControllerManager;
import cesc.shang.baselib.support.manager.HandlerManager;
import cesc.shang.baselib.support.manager.UtilsManager;

/**
 * Created by shanghaolongteng on 2017/7/15.
 */

public interface INotContextSupport {
    /**
     * 返回当前对象的Application
     */
    BaseApplication getApp(Context context);

    /**
     * 返回当前对象的Application中的ControllerManager
     */
    ControllerManager getControllerManager(Context context);

    /**
     * 返回当前对象的Application中的HandlerManager
     */
    HandlerManager getHandlerManager(Context context);

    /**
     * 返回当前对象的Application中的UtilsManager
     */
    UtilsManager getUtilsManager(Context context);
}
