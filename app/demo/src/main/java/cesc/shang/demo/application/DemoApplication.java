package cesc.shang.demo.application;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.base.crash.BaseCrashHandler;
import cesc.shang.baselib.support.manager.HandlerManager;
import cesc.shang.baselib.support.manager.UtilsManager;
import cesc.shang.demo.controller.AppController;

/**
 * Created by shanghaolongteng on 2017/7/16.
 */

public class DemoApplication extends BaseApplication<AppController, HandlerManager, UtilsManager> {
    @Override
    protected UtilsManager initUtilsManager() {
        return new UtilsManager(this);
    }

    @Override
    protected HandlerManager initHandlerManager() {
        return new HandlerManager(this);
    }

    @Override
    protected AppController initControllerManager() {
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
