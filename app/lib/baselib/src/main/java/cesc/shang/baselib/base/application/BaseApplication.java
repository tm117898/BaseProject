package cesc.shang.baselib.base.application;

import android.content.Context;

import cesc.shang.baselib.support.manager.ControllerManager;
import cesc.shang.baselib.support.manager.HandlerManager;
import cesc.shang.baselib.support.manager.UtilsManager;
import cesc.shang.utilslib.utils.debug.LogUtils;

/**
 * Created by shanghaolongteng on 2016/7/14.
 */
public abstract class BaseApplication<C extends ControllerManager, H extends HandlerManager, U extends UtilsManager> extends MultiDexApplication implements cesc.shang.baselib.support.IContextSupport {
    protected C c;
    protected H h;
    protected U u;
    protected LogUtils mLog;

    @Override
    public void onCreate() {
        super.onCreate();
        initSupport();
        mLog = u.getLogUtils(getClass().getSimpleName());
        mLog.i("create()");
        initDebug();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mLog.e("onLowMemory()");

        c.destroy();
        h.destroy();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        mLog.e("onTrimMemory() , level : ", level);
        if (level == TRIM_MEMORY_COMPLETE) {
            onLowMemory();
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mLog.e("onTerminate()");
    }

    protected void initSupport() {
        c = initControllerManager();
        h = initHandlerManager();
        u = initUtilsManager();
    }

    protected abstract U initUtilsManager();

    protected abstract H initHandlerManager();

    protected abstract C initControllerManager();

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

    public C getControllerManager() {
        return c;
    }

    @Override
    public H getHandlerManager() {
        return h;
    }

    @Override
    public U getUtilsManager() {
        return u;
    }
}
