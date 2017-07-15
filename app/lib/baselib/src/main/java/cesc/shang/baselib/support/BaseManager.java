package cesc.shang.baselib.support;

import cesc.shang.baselib.base.application.BaseApplication;

/**
 * Created by shanghaolongteng on 2017/7/15.
 */

public class BaseManager {
    protected BaseApplication mApp;

    public BaseManager(BaseApplication app) {
        onCreate(app);
    }

    protected void onCreate(BaseApplication app) {
        mApp = app;
    }

    protected void onDestroy() {
        mApp = null;
    }
}
