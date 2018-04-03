package cesc.shang.baselib.base.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.support.context.IContextSupport;
import cesc.shang.baselib.support.manager.ControllerManager;
import cesc.shang.baselib.support.manager.HandlerManager;
import cesc.shang.baselib.support.manager.UtilsManager;
import cesc.shang.utilslib.utils.debug.LogUtils;

/**
 * Created by shanghaolongteng on 2016/8/5.
 */
public abstract class BaseViewGroup extends ViewGroup implements IContextSupport {
    protected LogUtils mLog;
    protected ViewTouchHelper mTouchHelper = null;

    public BaseViewGroup(Context context) {
        this(context, null);
    }

    public BaseViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUtils();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BaseViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initUtils();
    }

    /**
     * 初始化工具
     */
    private void initUtils() {
        mLog = getUtilsManager().getLogUtils(this.getClass().getSimpleName());
    }

    /**
     * 是否开启使用辅助类
     *
     * @return true开启，false不开启
     */
    protected abstract boolean enableTouchHelper();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mLog.i("onMeasure , widthMeasureSpec : ", widthMeasureSpec, " , heightMeasureSpec : ", heightMeasureSpec);
        setMeasuredDimension(
                getSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getSize(getSuggestedMinimumHeight(), heightMeasureSpec)
        );
        onMeasureChild(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 根据测量模式与支持大小返回测量大小
     *
     * @param size        支持大小
     * @param measureSpec 测量模式
     * @return 实际测量大小
     */
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
            default:
                result = specSize;
                break;
        }
        return result;
    }

    /**
     * 当布局为wrap_content时，返回大小
     *
     * @param parentSize 父窗口大小
     * @return 包裹内容大小
     */
    protected abstract int getSizeByAtMost(int parentSize);

    /**
     * 测量子View
     *
     * @param widthMeasureSpec  宽测量模式
     * @param heightMeasureSpec 高测量模式
     */
    protected void onMeasureChild(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mLog.i("onLayout , changed : ", changed, " , left : ", left, " , top : ", top, " , right : ", right,
                " , bottom : ", bottom);
        if (changed) {
            onLayouting(left, top, right, bottom);
        }
    }

    /**
     * 如有需要可Override这个方法，onLayout
     *
     * @param left   x
     * @param top    y
     * @param right  width
     * @param bottom height
     */
    protected abstract void onLayouting(int left, int top, int right, int bottom);

    @Override
    protected void onAttachedToWindow() {
        mLog.i("onAttachedToWindow");
        super.onAttachedToWindow();

        if (enableTouchHelper()) {
            mTouchHelper = new ViewTouchHelper(getContext());
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mLog.i("onDetachedFromWindow");
        super.onDetachedFromWindow();

        if (enableTouchHelper()) {
            mTouchHelper.destroy();
            mTouchHelper = null;
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
        mLog.i("dispatchTouchEvent , event : ", event.getAction(), " , event.getX() : ", event.getX(),
                " , event.getY() : ", event.getY());
        return onDispatchingTouchEvent(event);
    }

    /**
     * 如有需要可Override这个方法，替代dispatchTouchEvent
     *
     * @param event 事件
     * @return 是否处理此事件
     */
    protected boolean onDispatchingTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public final boolean onInterceptTouchEvent(MotionEvent event) {
        mLog.i("onInterceptTouchEvent , event : ", event.getAction(), " , event.getX() : ", event.getX(),
                " , event.getY() : ", event.getY());
        return onInterceptingTouchEvent(event);
    }

    /**
     * 如有需要可Override这个方法，替代onInterceptTouchEvent
     *
     * @param event 事件
     * @return 是否处理此事件
     */
    protected boolean onInterceptingTouchEvent(MotionEvent event) {
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public final boolean onTouchEvent(MotionEvent event) {
        mLog.i("onTouchEvent , event : ", event.getAction(), " , event.getX() : ", event.getX(),
                " , event.getY() : ", event.getY());
        if (enableTouchHelper()) {
            mTouchHelper.onTouchEvent(event);
        }
        return onTouchingEvent(event);
    }

    /**
     * 如有需要可Override这个方法，替代onTouchEvent
     *
     * @param event 事件
     * @return 是否处理此事件
     */
    protected boolean onTouchingEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public BaseApplication getApp() {
        return (BaseApplication) getContext().getApplicationContext();
    }

    @Override
    public ControllerManager getControllerManager() {
        return getApp().getControllerManager();
    }

    @Override
    public HandlerManager getHandlerManager() {
        return getApp().getHandlerManager();
    }

    @Override
    public UtilsManager getUtilsManager() {
        return getApp().getUtilsManager();
    }
}
