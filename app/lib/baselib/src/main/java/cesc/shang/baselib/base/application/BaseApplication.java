package cesc.shang.baselib.base.application;

import android.content.Context;

import cesc.shang.baselib.support.BaseContextSupport;
import cesc.shang.baselib.support.controller.AppController;
import cesc.shang.baselib.support.manager.AppManager;
import cesc.shang.baselib.support.utils.UtilsManager;
import cesc.shang.utilslib.utils.debug.LogUtils;

/**
 * Created by shanghaolongteng on 2016/7/14.
 */
public abstract class BaseApplication<C extends AppController, M extends AppManager, U extends UtilsManager> extends MultiDexApplication implements BaseContextSupport {
    protected C c;
    protected M m;
    protected U u;
    protected LogUtils mLog;

    @Override
    public void onCreate() {
        super.onCreate();
        initSupport();
        mLog = u.getLogUtils(getClass().getSimpleName());
        mLog.i("onCreate()");
        initDebug();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mLog.e("onLowMemory()");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        mLog.e("onTrimMemory() , level : ", level);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mLog.e("onTerminate()");
    }

    protected void initSupport() {
        c = initAppController();
        m = initAppManager();
        u = initUtilsManager();
    }

    protected abstract U initUtilsManager();

    protected abstract M initAppManager();

    protected abstract C initAppController();

    protected void initDebug() {
        Thread.UncaughtExceptionHandler handler = getCrashHandler();
        if (handler != null) {
            Thread.setDefaultUncaughtExceptionHandler(handler);
        }
        if (enableStrictMode()) {
            u.getStrictModeUtils().enableStrictMode();
        }
    }

    protected abstract Thread.UncaughtExceptionHandler getCrashHandler();

    protected abstract boolean enableStrictMode();

    @Override
    public Context getContext() {
        return getApp();
    }

    @Override
    public BaseApplication getApp() {
        return this;
    }

    @Override
    public AppController getAppController() {
        return c;
    }

    @Override
    public AppManager getAppManager() {
        return m;
    }

    @Override
    public UtilsManager getUtilsManager() {
        return u;
    }
}
