package cesc.shang.demo.examples.view.conflict.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import cesc.shang.baselib.base.view.BaseViewGroup;

/**
 * Created by shanghaolongteng on 2016/8/9.
 */
public class VerticalViewGroup extends BaseViewGroup {
    private static final float OVER_PULL_COEFFICIENT = 0.5F;

    private int mMinY = -1, mMaxY = -1;
    private Scroller mScroller = null;

    public VerticalViewGroup(Context context) {
        super(context);
    }

    public VerticalViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VerticalViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public VerticalViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected boolean enableTouchUtils() {
        return true;
    }

    @Override
    protected int getSizeByAtMost(int parentSize) {
        return parentSize;
    }

    @Override
    protected void onLayouting(int left, int top, int right, int bottom) {
        mMinY = left;

        int childCount = getChildCount();
        int x = 0, y = 0;
        for (int i = 0; i < childCount; i++) {
            if (i > 0) {
                View lastChild = getChildAt(i - 1);
//                x += lastChild.getMeasuredWidth();
                y += lastChild.getMeasuredHeight();
            }
            mLog.i("onLayouting , x : ", x, " , y : ", y);
            View child = getChildAt(i);
            child.layout(x, y, x + child.getMeasuredWidth(), y + child.getMeasuredHeight());
        }

        View lastChild = getChildAt(childCount - 1);
        mMaxY = (int) lastChild.getY();
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
                if (mTouchUtils.isScroll()) {
                    float moveTagY = getScrollY() - mTouchUtils.getMoveY();
                    float moveY = -mTouchUtils.getMoveY();
                    if (mMinY > moveTagY) {
                        moveY *= OVER_PULL_COEFFICIENT;
//                        scrollTo(mMinY, 0);
                    } else if (moveTagY > mMaxY) {
                        moveY *= OVER_PULL_COEFFICIENT;
//                        scrollTo(mMaxY, 0);
                    }
                    scrollBy(0, (int) moveY);
                }
                break;
            case MotionEvent.ACTION_UP:
                boolean findChild = false;
                int curY = getScrollY();
                int childCount = getChildCount();

                if (mTouchUtils.isFling()) {
                    float moveY = mTouchUtils.getLastY() - mTouchUtils.getFirstY();

                    for (int i = 0; i < childCount; i++) {
                        View child = getChildAt(i);
                        float childY = child.getY();
                        float childHeightY = childY + child.getMeasuredHeight();

                        if (childY < curY && curY <= childHeightY) {
                            int scrollToTagChildIndex = i;
                            if (moveY < 0) {
                                scrollToTagChildIndex = getScrollToTagChildIndex(i, false);
                            } else if (moveY > 0) {
                                scrollToTagChildIndex = getScrollToTagChildIndex(i, true);
                            }
                            smoothScrollTo((int) (getChildAt(scrollToTagChildIndex).getY() - curY));
                            findChild = true;
                            break;
                        }
                    }
                } else if (mTouchUtils.isScroll()) {
                    for (int i = 0; i < childCount; i++) {
                        View child = getChildAt(i);
                        float childY = child.getY();
                        float childHeightY = childY + child.getMeasuredHeight();
                        float childHalfHeightY = childY + child.getMeasuredHeight() / 2;

                        if (childY <= curY && curY <= childHalfHeightY) {
                            smoothScrollTo((int) childY - curY);
                            findChild = true;
                            break;
                        } else if (childHalfHeightY < curY && curY <= childHeightY) {
                            smoothScrollTo((int) childHeightY - curY);
                            findChild = true;
                            break;
                        }
                    }
                }

                if (!findChild) {
                    if (curY < 0) {
                        smoothScrollTo(-curY);
                    } else {
                        View lastChild = getChildAt(childCount - 1);
                        float lastY = lastChild.getY() + lastChild.getMeasuredHeight();
                        if (curY > lastY) {
                            smoothScrollTo((int) (lastY - curY));
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

    private void smoothScrollTo(int moveY) {
        mScroller.startScroll(0, getScrollY(), 0, moveY, 1000);
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
