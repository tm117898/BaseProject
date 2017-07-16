package cesc.shang.demo.examples.view.conflict.exteriorintercept;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import cesc.shang.demo.examples.view.conflict.base.VerticalViewGroup;


/**
 * Created by shanghaolongteng on 2016/8/9.
 */
public class ExteriorInterceptGroup extends VerticalViewGroup {
    public ExteriorInterceptGroup(Context context) {
        super(context);
    }

    public ExteriorInterceptGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExteriorInterceptGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ExteriorInterceptGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private float mFirstX = 0, mFirstY = 0;
    private final int NORMAL_STATUS = 0, TOUCH_STATUE = 1, TOUCH_CHILD_STATUS = 2;
    private int status = NORMAL_STATUS;

    @Override
    protected boolean onInterceptingTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            status = NORMAL_STATUS;
            mFirstX = event.getX();
            mFirstY = event.getY();
            onTouchEvent(event);
        } else if (event.getAction() == MotionEvent.ACTION_MOVE && status == NORMAL_STATUS) {
            float touchX = mFirstX - event.getX();
            float touchY = mFirstY - event.getY();
            if (Math.abs(touchX) < Math.abs(touchY)) {
                mLog.i("test TOUCH_STATUE");
                status = TOUCH_STATUE;
            } else if (Math.abs(touchX) > Math.abs(touchY)) {
                mLog.i("test TOUCH_CHILD_STATUS");
                status = TOUCH_CHILD_STATUS;
            }
        }

        if (status == TOUCH_STATUE) {
            return true;
        } else {
            return super.onInterceptingTouchEvent(event);
        }
    }
}
