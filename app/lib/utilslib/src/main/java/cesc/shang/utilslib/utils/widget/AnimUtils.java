package cesc.shang.utilslib.utils.widget;

import android.animation.Animator;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;

/**
 * Created by shanghaolongteng on 2016/8/12.
 */
public class AnimUtils {
    public AnimUtils() {
    }

    public void stopAnimation(View view) {
        Animation a = view.getAnimation();
        if (a != null && a.hasStarted()) {
            a.cancel();
        }
    }

    public void stopBackgroundAnimation(View view) {
        Drawable ad = view.getBackground();
        if (ad instanceof AnimationDrawable) {
            ((AnimationDrawable) ad).stop();
        }
    }

    public void stopAnimator(Animator animator) {
        if (animator != null && animator.isRunning())
            animator.cancel();
    }
}
