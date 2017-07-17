package cesc.shang.baselib.support.manager;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.support.BaseManager;

/**
 * Created by shanghaolongteng on 2017/7/15.
 */

public class AppManager extends BaseManager {
    protected HttpManager mHttpManager;

    public AppManager(BaseApplication app) {
        super(app);
    }

    public HttpManager getHttpManager() {
        if (mHttpManager == null) {
            mHttpManager = new HttpManager(mApp);
        }
        return mHttpManager;
    }
}
