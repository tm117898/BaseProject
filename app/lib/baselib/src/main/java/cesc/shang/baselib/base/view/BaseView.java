package cesc.shang.baselib.base.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.support.BaseContextSupport;
import cesc.shang.baselib.support.controller.AppController;
import cesc.shang.baselib.support.manager.AppManager;
import cesc.shang.baselib.support.utils.UtilsManager;
import cesc.shang.utilslib.utils.debug.LogUtils;
import cesc.shang.utilslib.utils.widget.ViewTouchUtils;

/**
 * Created by shanghaolongteng on 2016/8/5.
 */
public abstract class BaseView extends View implements BaseContextSupport {
    protected LogUtils mLog;
    protected ViewTouchUtils mTouchUtils = null;

    public BaseView(Context context) {
        this(context, null);
    }

    public BaseView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUtils();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BaseView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initUtils();
    }

    private void initUtils() {
        mLog = getUtilsManager().getLogUtils(this.getClass().getSimpleName());
    }

    protected abstract boolean enableTouchUtils();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mLog.i("onMeasure , widthMeasureSpec : ", widthMeasureSpec, " , heightMeasureSpec : ", heightMeasureSpec);
        setMeasuredDimension(getSize(getSuggestedMinimumWidth(), widthMeasureSpec), getSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    private int getSize(int size, int measureSpec) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
                getSizeByAtMost(specSize);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    protected abstract int getSizeByAtMost(int parentSize);

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mLog.i("onLayout , changed : ", changed, " , left : ", left, " , top : ", top, " , right : ", right, " , bottom : ", bottom);
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onAttachedToWindow() {
        mLog.i("onAttachedToWindow");
        super.onAttachedToWindow();

        if (enableTouchUtils()) {
            mTouchUtils = getUtilsManager().getViewTouchUtils(getContext());
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mLog.i("onDetachedFromWindow");
        super.onDetachedFromWindow();

        if (enableTouchUtils()) {
            mTouchUtils.destroy();
            mTouchUtils = null;
        }
    }

    @Override
    public void scrollBy(int x, int y) {
        mLog.i("scrollBy , x : ", x, " , y : ", y);
        super.scrollBy(x, y);
    }

    @Override
    public void scrollTo(int x, int y) {
        mLog.i("scrollTo , x : ", x, " , y : ", y);
        super.scrollTo(x, y);
    }

    @Override
    public final boolean dispatchTouchEvent(MotionEvent event) {
        mLog.i("dispatchTouchEvent , event : ", event.getAction(), " , event.getX() : ", event.getX(), " , event.getY() : ", event.getY());
        return onDispatchingTouchEvent(event);
    }

    protected boolean onDispatchingTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public final boolean onTouchEvent(MotionEvent event) {
        mLog.i("onTouchEvent , event : ", event.getAction(), " , event.getX() : ", event.getX(), " , event.getY() : ", event.getY());
        if (enableTouchUtils()) {
            mTouchUtils.onTouchEvent(event);
        }
        return onTouchingEvent(event);
    }

    protected boolean onTouchingEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public BaseApplication getApp() {
        return (BaseApplication) getContext().getApplicationContext();
    }

    @Override
    public AppController getAppController() {
        return getApp().getAppController();
    }

    @Override
    public AppManager getAppManager() {
        return getApp().getAppManager();
    }

    @Override
    public UtilsManager getUtilsManager() {
        return getApp().getUtilsManager();
    }
}
