package cesc.shang.utilslib.utils.widget;

import android.content.Context;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

/**
 * Created by shanghaolongteng on 2016/8/7.
 */
public class ViewTouchUtils {
    private int mPagingTouchSlop;
    private int mMinimumFlingVelocity;
    private int mMaximumFlingVelocity;

    private float mFirstX = -1, mFirstY = -1;
    private float mLastX = -1, mLastY = -1;
    private float mMoveX = -1, mMoveY = -1;

    private VelocityTracker mTracker = null;

    private boolean mIsFling = false, mIsScroll = false;

    public ViewTouchUtils(Context context) {
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mPagingTouchSlop = configuration.getScaledPagingTouchSlop();
        mMinimumFlingVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumFlingVelocity = configuration.getScaledMaximumFlingVelocity();

        mTracker = VelocityTracker.obtain();
    }

    public void onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mTracker.addMovement(event);
                mFirstX = mLastX = x;
                mFirstY = mLastY = y;
                mMoveX = -1;
                mMoveY = -1;
                mIsFling = false;
                mIsScroll = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mIsFling) {
                    mTracker.addMovement(event);
                    mTracker.computeCurrentVelocity(1000, mMaximumFlingVelocity);
                    float xVelocity = mTracker.getXVelocity();
                    float yVelocity = mTracker.getYVelocity();
                    if (xVelocity > mMinimumFlingVelocity || yVelocity > mMinimumFlingVelocity) {
                        mIsFling = true;
                    }
                }

                if (mIsScroll) {
                    mMoveX = x - mLastX;
                    mMoveY = y - mLastY;
                } else {
                    float moveX = Math.abs(mLastX - mFirstX);
                    float moveY = Math.abs(mLastY - mFirstY);
                    if (moveX > mPagingTouchSlop || moveY > mPagingTouchSlop) {
                        mIsScroll = true;
                    }
                }

                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                if (!mIsFling) {
                    mTracker.addMovement(event);
                    mTracker.computeCurrentVelocity(1000, mMaximumFlingVelocity);
                    float xVelocity = mTracker.getXVelocity();
                    float yVelocity = mTracker.getYVelocity();
                    if (xVelocity > mMinimumFlingVelocity || yVelocity > mMinimumFlingVelocity) {
                        mIsFling = true;
                    }
                }
                mTracker.clear();

                if (mIsScroll) {
                    mMoveX = x - mLastX;
                    mMoveY = y - mLastY;
                }

                mLastX = x;
                mLastY = y;
                break;
        }
    }

    public void destroy() {
        mTracker.recycle();
    }

    public float getFirstX() {
        return mFirstX;
    }

    public float getFirstY() {
        return mFirstY;
    }

    public float getLastX() {
        return mLastX;
    }

    public float getLastY() {
        return mLastY;
    }

    public float getMoveX() {
        return mMoveX;
    }

    public float getMoveY() {
        return mMoveY;
    }

    public boolean isScroll() {
        return mIsScroll;
    }

    public boolean isFling() {
        return mIsFling;
    }
}
