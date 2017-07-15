package cesc.shang.baselib.support;

import android.content.Context;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.support.controller.AppController;
import cesc.shang.baselib.support.manager.AppManager;
import cesc.shang.baselib.support.utils.UtilsManager;

/**
 * Created by shanghaolongteng on 2017/7/15.
 */

public interface BaseContextSupport {
    Context getContext();

    BaseApplication getApp();

    AppController getAppController();

    AppManager getAppManager();

    UtilsManager getUtilsManager();
}
