package cesc.shang.demo.examples.okhttp;

import android.view.View;

import butterknife.OnClick;
import cesc.shang.demo.R;
import cesc.shang.demo.base.DemoBaseActivity;

/**
 * Created by Cesc Shang on 2017/7/17.
 */

public class OkHttpActivity extends DemoBaseActivity {
    @Override
    public int getContentViewId() {
        return R.layout.ok_http_layout;
    }

    @OnClick({R.id.get_request_button, R.id.post_request_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_request_button:
                getControllerManager().getOkHttpController().get();
                break;
            case R.id.post_request_button:
                getControllerManager().getOkHttpController().post();
                break;
        }
    }
}
