package cesc.shang.demo.examples.view.event;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import cesc.shang.baselib.base.view.BaseView;


/**
 * Created by shanghaolongteng on 2016/8/8.
 */
public class TouchEventView extends BaseView {
    public TouchEventView(Context context) {
        super(context);
    }

    public TouchEventView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchEventView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TouchEventView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected boolean enableTouchHelper() {
        return false;
    }

    @Override
    protected int getSizeByAtMost(int parentSize) {
        return 0;
    }

    @Override
    protected boolean onDispatchingTouchEvent(MotionEvent event) {
        mLog.i("TouchEvent , TouchEventView , onDispatchingTouchEvent ,  event : " + event);
        return super.onDispatchingTouchEvent(event);
    }

    @Override
    protected boolean onTouchingEvent(MotionEvent event) {
        mLog.i("TouchEvent , TouchEventView , onTouchingEvent ,  event : " + event);
        return super.onTouchingEvent(event);
    }
}
