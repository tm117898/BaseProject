package cesc.shang.demo.examples.switchview;

import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import butterknife.BindView;
import butterknife.OnClick;
import cesc.shang.demo.R;
import cesc.shang.demo.base.DemoBaseActivity;

/**
 * Created by shanghaolongteng on 2016/7/15.
 */
public class SwitchViewActivity extends DemoBaseActivity {
    @BindView(R.id.text_switcher)
    TextSwitcher mTextSwitcher;
    @BindView(R.id.image_switcher)
    ImageSwitcher mImageSwitcher;

    @Override
    public int getContentViewId() {
        return R.layout.switch_view_activty_layout;
    }

    @Override
    public void setupView() {
        mTextSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                return new TextView(SwitchViewActivity.this);
            }
        });

        mImageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                return new ImageView(SwitchViewActivity.this);
            }
        });
    }

    @OnClick(value = {R.id.text_switcher, R.id.image_switcher})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_switcher:
                mTextSwitcher.setText("shdfjahfpewufwefuh");
                break;
            case R.id.image_switcher:
                mImageSwitcher.setImageResource(R.mipmap.ic_launcher);
                break;
        }
    }
}
