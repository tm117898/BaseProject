package cesc.shang.demo.examples.view.event;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import cesc.shang.baselib.base.view.BaseViewGroup;


/**
 * Created by shanghaolongteng on 2016/8/8.
 */
public class TouchEventViewGroup extends BaseViewGroup {
    public TouchEventViewGroup(Context context) {
        super(context);
    }

    public TouchEventViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchEventViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TouchEventViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
    protected void onLayouting(int left, int top, int right, int bottom) {
        int childCount = getChildCount();
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            child.layout((width - childWidth) / 2, (height - childHeight) / 2, child.getMeasuredWidth(), child.getMeasuredHeight());
        }
    }

    @Override
    protected boolean onDispatchingTouchEvent(MotionEvent event) {
        mLog.i("TouchEvent , TouchEventView , onDispatchingTouchEvent ,  event : " + event);
        return super.onDispatchingTouchEvent(event);
    }

    @Override
    protected boolean onInterceptingTouchEvent(MotionEvent event) {
        mLog.i("TouchEvent , TouchEventView , onInterceptingTouchEvent ,  event : " + event);
        return super.onInterceptingTouchEvent(event);
    }

    @Override
    protected boolean onTouchingEvent(MotionEvent event) {
        mLog.i("TouchEvent , TouchEventView , onTouchingEvent ,  event : " + event);
        return super.onTouchingEvent(event);
    }
}
