package cesc.shang.baselib.base.application;

import android.app.Application;
import android.content.Context;

import cesc.shang.baselib.support.context.IContextSupport;
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
        extends Application implements IContextSupport {
    protected C c;
    protected H h;
    protected U u;
    protected LogUtils mLog;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        init();
    }

    /**
     * application初始化。
     * 之所以在attachBaseContext中初始化，是因为Provider的onCreate（）执行早于Application的onCreate（）。
     */
    private void init() {
        initSupport();
        mLog = u.getLogUtils(getClass().getSimpleName());
        mLog.i("create()");
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

    @Override
    public Context getContext() {
        return getApp();
    }

    @Override
    public BaseApplication getApp() {
        return this;
    }

    @Override
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
