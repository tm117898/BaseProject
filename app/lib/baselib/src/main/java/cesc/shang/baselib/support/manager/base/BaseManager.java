package cesc.shang.baselib.support.manager.base;

import cesc.shang.baselib.base.application.BaseApplication;

/**
 * Created by shanghaolongteng on 2017/7/15.
 */

public abstract class BaseManager {
    private BaseApplication mApp;

    public BaseManager(BaseApplication app) {
        create(app);
    }

    protected void create(BaseApplication app) {
        mApp = app;
    }

    public void destroy() {
        onDestroy();
        mApp = null;
    }

    public abstract void onDestroy();

    public BaseApplication getApp() {
        return mApp;
    }

    /**
     * 释放Manager资源
     *
     * @param m Manager实例
     */
    protected void destroyManager(BaseManager m) {
        if (m != null) {
            m.destroy();
        }
    }
}
