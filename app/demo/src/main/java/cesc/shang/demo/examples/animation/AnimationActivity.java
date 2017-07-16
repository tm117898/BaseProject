package cesc.shang.demo.examples.animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.OnClick;
import cesc.shang.baselib.base.animation.Rotate3dAnimation;
import cesc.shang.demo.R;
import cesc.shang.demo.base.DemoBaseActivity;
import cesc.shang.utilslib.utils.widget.AnimUtils;

/**
 * Created by shanghaolongteng on 2016/8/12.
 */
public class AnimationActivity extends DemoBaseActivity {
    @BindView(R.id.anim_image)
    ImageView animImage;
    @BindView(R.id.frame_anim_image)
    ImageView frameAnimImage;
    @BindView(R.id.custom_anim_image)
    ImageView customAnimImage;
    @BindView(R.id.object_anim_image)
    ImageView objectAnimImage;

    @Override
    public int getContentViewId() {
        return R.layout.animation_activty_layout;
    }

    @OnClick({R.id.anim_image, R.id.frame_anim_image, R.id.custom_anim_image, R.id.object_anim_image})
    public void onClick(View view) {
        Animation a = null;
        switch (view.getId()) {
            case R.id.anim_image:
                a = AnimationUtils.loadAnimation(this, R.anim.animation_activity_anim);
                animImage.startAnimation(a);
                break;
            case R.id.custom_anim_image:
                a = new Rotate3dAnimation(this, 0, 180, customAnimImage.getMeasuredWidth() / 2, customAnimImage.getMeasuredHeight() / 2, 310.0f, false);
                a.setDuration(1000);
                a.setFillAfter(true);
                a.setInterpolator(new AccelerateInterpolator());
                customAnimImage.startAnimation(a);
                break;
            case R.id.frame_anim_image:
                AnimationDrawable ad = (AnimationDrawable) frameAnimImage.getBackground();
                ad.start();
                break;
            case R.id.object_anim_image:
                at = AnimatorInflater.loadAnimator(this, R.animator.animation_activity_object_anim);
                at.setTarget(objectAnimImage);
                at.start();
                break;
        }
    }

    Animator at = null;

    @Override
    protected void onDestroy() {
        AnimUtils utils = getUtilsManager().getAnimUtils();
        utils.stopAnimation(animImage);
        utils.stopAnimation(customAnimImage);
        utils.stopBackgroundAnimation(frameAnimImage);
        utils.stopAnimator(at);

        super.onDestroy();
    }
}
