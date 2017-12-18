package cesc.shang.baselib.base.popup;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import cesc.shang.baselib.base.activity.BaseActivity;

/**
 * Created by shanghaolongteng on 2017/7/22.
 */

public abstract class BasePopupWindow extends PopupWindow {
    protected BaseActivity mActivity = null;

    public BasePopupWindow(BaseActivity activity) {
        super();
        mActivity = activity;
        setupPopupWindow();
        View view = LayoutInflater.from(activity).inflate(getLayoutId(), null, false);
        setContentView(view);
        setupView(view);
    }

    /**
     * 设置PopupWindow相关属性
     */
    private void setupPopupWindow() {
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setOutsideTouchable(false);
    }

    /**
     * 获取PopupWindow布局Id
     *
     * @return layoutId
     */
    protected abstract int getLayoutId();

    /**
     * 处理View相关操作，如View.setOnClickLister() or AdapterView.setAdapter() or ...
     *
     * @param rootView Fragment根布局
     */
    protected void setupView(View rootView) {
    }

    /**
     * 显示
     *
     * @param parent 父布局
     */
    public void show(View parent) {
        showAtLocation(parent, Gravity.CENTER, 0, 0);
    }

    /**
     * 释放资源
     */
    public void release() {
        mActivity = null;
        dismiss();
    }
}
