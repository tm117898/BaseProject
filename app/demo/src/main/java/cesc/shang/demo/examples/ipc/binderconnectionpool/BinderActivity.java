package cesc.shang.demo.examples.ipc.binderconnectionpool;

import android.view.View;

import butterknife.OnClick;
import cesc.shang.demo.R;
import cesc.shang.demo.base.DemoBaseActivity;

/**
 * Created by shanghaolongteng on 2016/8/4.
 */
public class BinderActivity extends DemoBaseActivity {
    @Override
    public int getContentViewId() {
        return R.layout.binder_actvity_layout;
    }

    @Override
    public void initData() {
        getBinderController().onActivityCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getBinderController().onActivityDestroy();
    }

    private BinderController getBinderController() {
        return getControllerManager().getBinderController();
    }

    @OnClick({R.id.get_first_binder, R.id.call_first_binder_method, R.id.get_second_binder, R.id.call_second_binder_method})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_first_binder:
                getBinderController().getFirstBinder();
                break;
            case R.id.call_first_binder_method:
                getBinderController().callFirstBinderMethod();
                break;
            case R.id.get_second_binder:
                getBinderController().getSecondBinder();
                break;
            case R.id.call_second_binder_method:
                getBinderController().callSecondBinderMethod();
                break;
        }
    }
}
