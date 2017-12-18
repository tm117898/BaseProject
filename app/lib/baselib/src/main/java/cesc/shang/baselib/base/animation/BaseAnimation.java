package cesc.shang.baselib.base.animation;

import android.view.animation.Animation;
import android.view.animation.Transformation;

import cesc.shang.baselib.support.IContextSupport;
import cesc.shang.utilslib.utils.debug.LogUtils;

/**
 * Created by shanghaolongteng on 2016/8/12.
 */
public abstract class BaseAnimation extends Animation {
    protected LogUtils mLog;

    public BaseAnimation(IContextSupport support) {
        mLog = support.getUtilsManager().getLogUtils(this.getClass().getSimpleName());
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mLog.i("initialize , width : ", width, " , height : ", height,
                " , parentWidth : ", parentWidth, " , parentHeight : ", parentHeight);
        onInitialize(width, height, parentWidth, parentHeight);
    }

    /**
     * 通常可进行另外的初始化工作
     *
     * @param width        将初始化动画组件宽
     * @param height       将初始化动画组件高
     * @param parentWidth  父容器的宽
     * @param parentHeight 父容器的高
     */
    protected abstract void onInitialize(int width, int height, int parentWidth, int parentHeight);

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        mLog.i("applyTransformation , interpolatedTime : ", interpolatedTime, " , t : ", t);
        onApplyTransformation(interpolatedTime, t);
    }

    /**
     * 在绘制动画的过程中会反复的调用的函数
     *
     * @param interpolatedTime 从0渐变为1，当该参数为1时表明动画结束
     * @param t                通过参数t来获取变换的矩阵（matrix），通过改变矩阵就可以实现各种复杂的效果
     */
    protected abstract void onApplyTransformation(float interpolatedTime, Transformation t);
}
