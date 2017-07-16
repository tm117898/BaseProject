package cesc.shang.demo.application;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.base.crash.BaseCrashHandler;
import cesc.shang.baselib.support.manager.AppManager;
import cesc.shang.baselib.support.utils.UtilsManager;
import cesc.shang.demo.controller.AppController;

/**
 * Created by shanghaolongteng on 2017/7/16.
 */

public class DemoApplication extends BaseApplication<AppController, AppManager, UtilsManager> {
    @Override
    protected UtilsManager initUtilsManager() {
        return new UtilsManager(this);
    }

    @Override
    protected AppManager initAppManager() {
        return new AppManager(this);
    }

    @Override
    protected AppController initAppController() {
        return new AppController(this);
    }

    @Override
    protected Thread.UncaughtExceptionHandler getCrashHandler() {
        return new BaseCrashHandler(this);
    }

    @Override
    protected boolean enableStrictMode() {
        return true;
    }
}
