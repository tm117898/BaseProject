package cesc.shang.baselib.support.manager.base;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.support.manager.UtilsManager;

/**
 * Created by Cesc Shang on 2017/12/13.
 */

public abstract class BaseHandler extends BaseManager {
    public BaseHandler(BaseApplication app) {
        super(app);
    }

    public UtilsManager getUtilsManager() {
        return getApp().getUtilsManager();
    }
}
