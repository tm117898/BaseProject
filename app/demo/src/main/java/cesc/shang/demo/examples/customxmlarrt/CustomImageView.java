package cesc.shang.demo.examples.customxmlarrt;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import cesc.shang.demo.R;

/**
 * Created by shanghaolongteng on 2016/7/15.
 */
public class CustomImageView extends android.support.v7.widget.AppCompatImageView {
    private Drawable mTestimg = null;

    public CustomImageView(Context context) {
        super(context);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCustomArrt(context, attrs);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCustomArrt(context, attrs);
    }

    private void initCustomArrt(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.custom_xml_arrt_name);
        try {
            mTestimg = a.getDrawable(R.styleable.custom_xml_arrt_name_test_img);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mTestimg.setBounds(0, 0, getMeasuredWidth(), getMeasuredWidth());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mTestimg.draw(canvas);
    }
}