package cesc.shang.demo.examples.view.move;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import cesc.shang.baselib.base.view.BaseViewGroup;

/**
 * Created by shanghaolongteng on 2016/8/5.
 */
public class TouchMoveViewGroup extends BaseViewGroup {
    private static final float OVER_PULL_COEFFICIENT = 0.5F;

    private int mMinX = -1, mMaxX = -1;
    private Scroller mScroller = null;

    public TouchMoveViewGroup(Context context) {
        super(context);
    }

    public TouchMoveViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchMoveViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TouchMoveViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected boolean enableTouchHelper() {
        return true;
    }

    @Override
    protected int getSizeByAtMost(int parentSize) {
        return parentSize;
    }

    @Override
    protected void onLayouting(int left, int top, int right, int bottom) {
        mMinX = left;

        int childCount = getChildCount();
        int x = left, y = top;
        for (int i = 0; i < childCount; i++) {
            if (i > 0) {
                View lastChild = getChildAt(i - 1);
                x += lastChild.getMeasuredWidth();
//                y += lastChild.getMeasuredHeight();
            }
            mLog.i("onLayouting , x : ", x, " , y : ", y);
            View child = getChildAt(i);
            child.layout(x, y, x + child.getMeasuredWidth(), y + child.getMeasuredHeight());
        }

        View lastChild = getChildAt(childCount - 1);
        mMaxX = (int) lastChild.getX();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mScroller = new Scroller(getContext());
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (!mScroller.isFinished())
            mScroller.forceFinished(true);
        mScroller = null;
    }

    @Override
    public boolean onTouchingEvent(MotionEvent event) {
        final int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished())
                    mScroller.abortAnimation();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mTouchHelper.isScroll()) {
                    float moveTagX = getScrollX() - mTouchHelper.getMoveX();
                    float moveX = -mTouchHelper.getMoveX();
                    if (mMinX > moveTagX) {
                        moveX *= OVER_PULL_COEFFICIENT;
//                        scrollTo(mMinX, 0);
                    } else if (moveTagX > mMaxX) {
                        moveX *= OVER_PULL_COEFFICIENT;
//                        scrollTo(mMaxX, 0);
                    }
                    scrollBy((int) moveX, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                boolean findChild = false;
                int curX = getScrollX();
                int childCount = getChildCount();

                if (mTouchHelper.isFling()) {
                    float moveX = mTouchHelper.getLastX() - mTouchHelper.getFirstX();

                    for (int i = 0; i < childCount; i++) {
                        View child = getChildAt(i);
                        float childX = child.getX();
                        float childWidthX = childX + child.getMeasuredWidth();

                        if (childX < curX && curX <= childWidthX) {
                            int scrollToTagChildIndex = i;
                            if (moveX < 0) {
                                scrollToTagChildIndex = getScrollToTagChildIndex(i, false);
                            } else if (moveX > 0) {
                                scrollToTagChildIndex = getScrollToTagChildIndex(i, true);
                            }
                            smoothScrollTo((int) (getChildAt(scrollToTagChildIndex).getX() - curX));
                            findChild = true;
                            break;
                        }
                    }
                } else if (mTouchHelper.isScroll()) {
                    for (int i = 0; i < childCount; i++) {
                        View child = getChildAt(i);
                        float childX = child.getX();
                        float childWidthX = childX + child.getMeasuredWidth();
                        float childHalfWidthX = childX + child.getMeasuredWidth() / 2;

                        if (childX <= curX && curX <= childHalfWidthX) {
                            smoothScrollTo((int) childX - curX);
                            findChild = true;
                            break;
                        } else if (childHalfWidthX < curX && curX <= childWidthX) {
                            smoothScrollTo((int) childWidthX - curX);
                            findChild = true;
                            break;
                        }
                    }
                }

                if (!findChild) {
                    if (curX < 0) {
                        smoothScrollTo(-curX);
                    } else {
                        View lastChild = getChildAt(childCount - 1);
                        float lastX = lastChild.getX() + lastChild.getMeasuredWidth();
                        if (curX > lastX) {
                            smoothScrollTo((int) (lastX - curX));
                        }
                    }
                }
                break;
        }
        return true;
    }

    private int getScrollToTagChildIndex(int childIndex, boolean moveToLeft) {
        int result = 0;
        if (moveToLeft) {
            if (childIndex == 0) {
                result = 0;
            } else {
                result = childIndex;
            }
        } else {
            if (childIndex == getChildCount() - 1) {
                result = getChildCount() - 1;
            } else {
                result = childIndex + 1;
            }
        }
        return result;
    }

    private void smoothScrollTo(int moveX) {
        mScroller.startScroll(getScrollX(), 0, moveX, 0, 1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
