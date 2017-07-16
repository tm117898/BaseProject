package cesc.shang.demo.examples.drawable;

import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.widget.ImageView;

import butterknife.BindView;
import cesc.shang.demo.R;
import cesc.shang.demo.base.DemoBaseActivity;

/**
 * Created by shanghaolongteng on 2016/8/11.
 */
public class DrawableActivity extends DemoBaseActivity {
    @BindView(R.id.level_list_image)
    ImageView levelListImage;
    @BindView(R.id.transition_image)
    ImageView transitionImage;
    @BindView(R.id.scale_image)
    ImageView scaleImage;
    @BindView(R.id.clip_image)
    ImageView clipImage;

    @Override
    public int getContentViewId() {
        return R.layout.drawable_activity_layout;
    }

    @Override
    public void initData() {
        levelListImage.setImageLevel(15);

        TransitionDrawable t = (TransitionDrawable) transitionImage.getBackground();
        t.startTransition(1000);

        ScaleDrawable s = (ScaleDrawable) scaleImage.getBackground();
        s.setLevel(1);//0~10000,0 is gone

        ClipDrawable c = (ClipDrawable) clipImage.getBackground();
        c.setLevel(3000);//0~10000,0 is all clip , 10000 is not clip
    }
}
