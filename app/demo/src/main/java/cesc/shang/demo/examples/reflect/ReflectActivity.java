package cesc.shang.demo.examples.reflect;

import android.widget.TextView;

import butterknife.BindView;
import cesc.shang.baselib.base.activity.BaseActivity;
import cesc.shang.demo.R;

/**
 * Created by shanghaolongteng on 2017/8/5.
 */

public class ReflectActivity extends BaseActivity {
    @BindView(R.id.info_text)
    TextView mInfoText;

    @Override
    public int getContentViewId() {
        return R.layout.reflect_ctivity_layout;
    }

    @Override
    public void initData() {
        super.initData();

        new Thread() {
            @Override
            public void run() {
                super.run();

                final String info = getUtilsManager().getReflectUtils().analyzeClass("cesc.shang.demo.controller.AppController");
                mLog.i("ReflectActivity , info : \n" + info);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mInfoText.setText(info);
                    }
                });
            }
        }.start();
    }
}
