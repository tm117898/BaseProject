package cesc.shang.baselib.base.animation;

import android.view.animation.Animation;
import android.view.animation.Transformation;

import cesc.shang.baselib.support.BaseContextSupport;
import cesc.shang.utilslib.utils.debug.LogUtils;

/**
 * Created by shanghaolongteng on 2016/8/12.
 */
public abstract class BaseAnimation extends Animation {
    protected LogUtils mLog;

    public BaseAnimation(BaseContextSupport support) {
        mLog = support.getUtilsManager().getLogUtils(this.getClass().getSimpleName());
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mLog.i("initialize , width : ", width, " , height : ", height, " , parentWidth : ", parentWidth, " , parentHeight : ", parentHeight);
        onInitialize(width, height, parentWidth, parentHeight);
    }

    protected abstract void onInitialize(int width, int height, int parentWidth, int parentHeight);

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        mLog.i("applyTransformation , interpolatedTime : ", interpolatedTime, " , t : ", t);
        onApplyTransformation(interpolatedTime, t);
    }

    protected abstract void onApplyTransformation(float interpolatedTime, Transformation t);
}
