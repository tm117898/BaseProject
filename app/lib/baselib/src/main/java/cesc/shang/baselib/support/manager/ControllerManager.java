package cesc.shang.baselib.support.manager;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.support.controller.InitController;
import cesc.shang.baselib.support.manager.base.BaseController;
import cesc.shang.baselib.support.manager.base.BaseManager;

/**
 * Created by shanghaolongteng on 2017/7/15.
 * <p>
 * 管理各个Controller类实例和生命周期
 */

public class ControllerManager extends BaseManager {
    private InitController mInit;

    public ControllerManager(BaseApplication app) {
        super(app);
    }

    @Override
    public void onDestroy() {

    }

    /**
     * 释放Handler资源
     *
     * @param c Handler实例
     */
    protected void destroyController(BaseController c) {
        destroyManager(c);
    }

    public synchronized InitController getInitController() {
        if (mInit == null) {
            mInit = new InitController(getApp());
        }
        return mInit;
    }
}
