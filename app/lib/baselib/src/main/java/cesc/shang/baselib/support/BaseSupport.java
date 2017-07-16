package cesc.shang.baselib.support;

import android.content.Context;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.support.manager.AppManager;
import cesc.shang.baselib.support.utils.UtilsManager;

/**
 * Created by shanghaolongteng on 2017/7/15.
 */

public interface BaseSupport {
    BaseApplication getApp(Context context);

    AppManager getAppManager(Context context);

    UtilsManager getUtilsManager(Context context);
}
