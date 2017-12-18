package cesc.shang.baselib.base.application;

import android.content.Context;

import cesc.shang.baselib.support.manager.ControllerManager;
import cesc.shang.baselib.support.manager.HandlerManager;
import cesc.shang.baselib.support.manager.UtilsManager;
import cesc.shang.utilslib.utils.debug.LogUtils;

/**
 * Created by shanghaolongteng on 2016/7/14.
 *
 * @param <C> ControllerManager 泛型
 * @param <H> HandlerManager 泛型
 * @param <U> UtilsManager 泛型
 */
public abstract class BaseApplication<C extends ControllerManager, H extends HandlerManager, U extends UtilsManager>
        extends MultiDexApplication implements cesc.shang.baselib.support.IContextSupport {
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

    /**
     * 初始化ControllerManager、HandlerManager、UtilsManager
     */
    protected void initSupport() {
        c = initControllerManager();
        h = initHandlerManager();
        u = initUtilsManager();
    }

    /**
     * 初始化ControllerManager
     *
     * @return ControllerManager或其子类
     */
    protected abstract U initUtilsManager();

    /**
     * 初始化HandlerManager
     *
     * @return HandlerManager或其子类
     */
    protected abstract H initHandlerManager();

    /**
     * 初始化UtilsManager
     *
     * @return UtilsManager或其子类
     */
    protected abstract C initControllerManager();

    /**
     * 设置CrashHandler与StrictMode
     */
    protected void initDebug() {
        Thread.UncaughtExceptionHandler handler = getCrashHandler();
        if (handler != null) {
            Thread.setDefaultUncaughtExceptionHandler(handler);
        }
        if (enableStrictMode()) {
            u.getStrictModeUtils().enableStrictMode();
        }
    }

    /**
     * 获取 CrashHandler 实例
     *
     * @return BaseCrashHandler或其子类
     */
    protected abstract Thread.UncaughtExceptionHandler getCrashHandler();

    /**
     * 是否开始严格模式
     *
     * @return true开启，false关闭
     */
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
