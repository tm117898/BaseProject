package cesc.shang.baselib.support;

import android.content.Context;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.support.manager.ControllerManager;
import cesc.shang.baselib.support.manager.HandlerManager;
import cesc.shang.baselib.support.manager.UtilsManager;

/**
 * Created by shanghaolongteng on 2017/7/15.
 */

public interface IContextSupport {

    /**
     * 返回当前对象的Context
     */
    Context getContext();

    /**
     * 返回当前对象的Application
     */
    BaseApplication getApp();

    /**
     * 返回当前对象的Application中的ControllerManager
     */
    ControllerManager getControllerManager();

    /**
     * 返回当前对象的Application中的HandlerManager
     */
    HandlerManager getHandlerManager();

    /**
     * 返回当前对象的Application中的UtilsManager
     */
    UtilsManager getUtilsManager();
}
