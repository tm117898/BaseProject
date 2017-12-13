package cesc.shang.demo.examples.ipc.aidl;

import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import cesc.shang.demo.R;
import cesc.shang.demo.base.DemoBaseActivity;

/**
 * Created by shanghaolongteng on 2016/7/31.
 */
public class AIDLActivity extends DemoBaseActivity {
    @BindView(R.id.send_button)
    Button mSendButton;
    @BindView(R.id.bind_button)
    Button mBindButton;

    @Override
    public int getContentViewId() {
        return R.layout.aidl_activity_layout;
    }

    @Override
    public void initData() {
        super.initData();
        getAidlController().onActivityCreate(this);
    }

    private AIDLController getAidlController() {
        return getControllerManager().getAIDLController();
    }

    @OnClick(value = {R.id.send_button, R.id.bind_button})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_button:
                getAidlController().sendEntity();
                break;
            case R.id.bind_button:
                getAidlController().bindService();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getAidlController().onActivityDestroy();
    }
}
