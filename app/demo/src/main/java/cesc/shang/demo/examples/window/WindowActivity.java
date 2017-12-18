package cesc.shang.demo.examples.window;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import butterknife.OnClick;
import cesc.shang.demo.R;
import cesc.shang.demo.base.DemoBaseActivity;

/**
 * Created by shanghaolongteng on 2016/8/12.
 */
public class WindowActivity extends DemoBaseActivity {
    private FrameLayout mFullView = null;
    private View mHalfView = null;
    private WindowManager.LayoutParams mFullLp = null, mHalfLp = null;

    @Override
    public int getContentViewId() {
        return R.layout.window_activity_layout;
    }

    @OnClick({R.id.show_full_screen_window_button, R.id.show_half_screen_window_button, R.id.dismiss_half_screen_window_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.show_full_screen_window_button:
                if (mFullView == null) {
                    mFullView = new FrameLayout(this);
                    mFullView.addView(getLayoutInflater().inflate(R.layout.window_activity_full_screen_layout, null, false));
                    mFullView.findViewById(R.id.dismiss_full_screen_window_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            WindowActivity.this.onClick(v);
                        }
                    });

                    mFullLp = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    mFullLp.format = PixelFormat.TRANSPARENT;
                    mFullLp.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                            | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;//可以现在是锁屏界面
                    mFullLp.gravity = Gravity.TOP | Gravity.LEFT;
                    mFullLp.x = 0;
                    mFullLp.y = 0;
                }
                getWindowManager().addView(mFullView, mFullLp);
                break;
            case R.id.dismiss_full_screen_window_button:
                getWindowManager().removeView(mFullView);
                break;
            case R.id.show_half_screen_window_button:
                if (mHalfView == null) {
                    mHalfView = new View(this);
                    mHalfView.setBackgroundColor(Color.BLUE);

                    mHalfLp = new WindowManager.LayoutParams();
                    mHalfLp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    mHalfLp.height = 500;
                    mHalfLp.format = PixelFormat.TRANSPARENT;
                    mHalfLp.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                            | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED; //可以现在是锁屏界面
                    mHalfLp.gravity = Gravity.TOP | Gravity.LEFT;
                    mHalfLp.x = 0;
                    mHalfLp.y = 0;
                }
                getWindowManager().addView(mHalfView, mHalfLp);
                break;
            case R.id.dismiss_half_screen_window_button:
                getWindowManager().removeView(mHalfView);
                break;
        }
    }

    @Override
    public WindowManager getWindowManager() {
        return (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//        return super.getWindowManager();
    }
}
