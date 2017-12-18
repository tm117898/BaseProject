package cesc.shang.demo.examples.view.move;

import android.content.Context;
import android.util.AttributeSet;

import cesc.shang.baselib.base.view.BaseView;


/**
 * Created by shanghaolongteng on 2016/8/5.
 */
public class TouchMoveView extends BaseView {
    public TouchMoveView(Context context) {
        super(context);
    }

    public TouchMoveView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchMoveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TouchMoveView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected boolean enableTouchHelper() {
        return false;
    }

    @Override
    protected int getSizeByAtMost(int parentSize) {
        return parentSize;
    }
}
