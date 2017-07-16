package cesc.shang.demo.examples.activitywwitchorder;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cesc.shang.demo.R;
import cesc.shang.demo.base.DemoBaseActivity;

/**
 * Created by shanghaolongteng on 2016/7/17.
 */
public class ActivitySwitchOrderActivity2 extends DemoBaseActivity {
    @BindView(R.id.text)
    TextView mText;

    @Override
    public int getContentViewId() {
        return R.layout.activity_switch_order_activty_layout;
    }

    @Override
    public void setupView() {
        super.setupView();
        mText.setText("start activity1 ");
    }

    @OnClick(value = R.id.text)
    public void onClick(View v) {
        startActivity(new Intent(ActivitySwitchOrderActivity2.this, ActivitySwitchOrderActivity1.class));
    }
}

