package cesc.shang.demo.examples.view.event;

import android.view.MotionEvent;

import cesc.shang.demo.R;
import cesc.shang.demo.base.DemoBaseActivity;

/**
 * Created by shanghaolongteng on 2016/8/8.
 */
public class TouchEventActivity extends DemoBaseActivity {
    @Override
    public int getContentViewId() {
        return R.layout.touch_event_activity_layout;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        mLog.i("TouchEvent , TouchEventActivity , dispatchTouchEvent ,  event : " + event);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mLog.i("TouchEvent , TouchEventActivity , onTouchEvent ,  event : " + event);
        return super.onTouchEvent(event);
    }
}
