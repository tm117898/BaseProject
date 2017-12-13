package cesc.shang.baselib.support.manager.base;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.support.manager.HandlerManager;

/**
 * Created by Cesc Shang on 2017/12/13.
 */

public abstract class BaseController extends BaseHandler {
    public BaseController(BaseApplication app) {
        super(app);
    }

    public HandlerManager getHandlerManager() {
        return getApp().getHandlerManager();
    }

    protected void destroyHandler(BaseManager m) {
        destroyManager(m);
    }
}
