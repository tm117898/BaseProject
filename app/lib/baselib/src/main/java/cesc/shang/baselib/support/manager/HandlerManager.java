package cesc.shang.baselib.support.manager;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.support.handler.HttpHandler;
import cesc.shang.baselib.support.manager.base.BaseHandler;
import cesc.shang.baselib.support.manager.base.BaseManager;

/**
 * Created by shanghaolongteng on 2017/7/15.
 * <p>
 * 管理各个Handler类实例和生命周期
 */

public class HandlerManager extends BaseManager {
    protected HttpHandler mHttpHandler;

    public HandlerManager(BaseApplication app) {
        super(app);
    }

    @Override
    public void onDestroy() {
        destroyHandler(mHttpHandler);
        mHttpHandler = null;
    }

    /**
     * 释放Handler资源
     *
     * @param h Handler实例
     */
    protected void destroyHandler(BaseHandler h) {
        destroyManager(h);
    }

    public synchronized HttpHandler getHttpManager() {
        if (mHttpHandler == null) {
            mHttpHandler = new HttpHandler(getApp());
        }
        return mHttpHandler;
    }
}
