package cesc.shang.baselib.base.view;

import android.content.Context;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

/**
 * Created by shanghaolongteng on 2016/8/7.
 */
public class ViewTouchHelper {
    public static final int INVALID_INT = -1;
    public static final int VELOCITY_UNITS = 1000;

    private VelocityTracker mTracker;
    private int mPagingTouchSlop;
    private int mMinimumFlingVelocity;
    private int mMaximumFlingVelocity;

    private float mFirstX = INVALID_INT;
    private float mFirstY = INVALID_INT;
    private float mLastX = INVALID_INT;
    private float mLastY = INVALID_INT;
    private float mMoveX = INVALID_INT;
    private float mMoveY = INVALID_INT;

    private boolean mIsFling = false;
    private boolean mIsScroll = false;

    public ViewTouchHelper(Context context) {
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mPagingTouchSlop = configuration.getScaledPagingTouchSlop();
        mMinimumFlingVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumFlingVelocity = configuration.getScaledMaximumFlingVelocity();

        mTracker = VelocityTracker.obtain();
    }

    /**
     * 处理touch事件
     *
     * @param event MotionEvent
     */
    public void onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                onTouchDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                onTouchMove(event);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                onTouchUp(event);
                break;
            default:
                break;
        }
    }

    /**
     * 处理down事件
     *
     * @param event MotionEvent
     */
    private void onTouchDown(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        mTracker.addMovement(event);
        mFirstX = mLastX = x;
        mFirstY = mLastY = y;
        mMoveX = INVALID_INT;
        mMoveY = INVALID_INT;
        mIsFling = false;
        mIsScroll = false;
    }

    /**
     * 处理move事件
     *
     * @param event MotionEvent
     */
    private void onTouchMove(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if (!mIsFling) {
            mTracker.addMovement(event);
            mTracker.computeCurrentVelocity(VELOCITY_UNITS, mMaximumFlingVelocity);
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
    }

    /**
     * 处理up事件
     *
     * @param event MotionEvent
     */
    private void onTouchUp(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if (!mIsFling) {
            mTracker.addMovement(event);
            mTracker.computeCurrentVelocity(VELOCITY_UNITS, mMaximumFlingVelocity);
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
    }

    /**
     * 释放资源
     */
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
