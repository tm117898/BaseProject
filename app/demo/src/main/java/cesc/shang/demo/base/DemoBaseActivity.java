package cesc.shang.demo.base;

import cesc.shang.baselib.base.activity.BaseActivity;
import cesc.shang.demo.controller.AppController;

/**
 * Created by shanghaolongteng on 2017/7/16.
 */

public abstract class DemoBaseActivity extends BaseActivity {
    public AppController getControllerManager() {
        return (AppController) super.getControllerManager();
    }
}
