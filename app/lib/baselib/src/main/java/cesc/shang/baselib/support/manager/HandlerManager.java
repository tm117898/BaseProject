package cesc.shang.baselib.support.manager;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.support.handler.HttpHandler;
import cesc.shang.baselib.support.manager.base.BaseManager;

/**
 * Created by shanghaolongteng on 2017/7/15.
 */

public class HandlerManager extends BaseManager {
    protected HttpHandler mHttpHandler;

    public HandlerManager(BaseApplication app) {
        super(app);
    }

    @Override
    public void onDestroy() {
        destroyManager(mHttpHandler);
        mHttpHandler = null;
    }

    public HttpHandler getHttpManager() {
        if (mHttpHandler == null) {
            mHttpHandler = new HttpHandler(getApp());
        }
        return mHttpHandler;
    }
}
