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
        View view = LayoutInflater.from(activity).inflate(getLayoutId(), null, false);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
    }

    protected abstract int getLayoutId();

    public void show(View parent) {
        showAtLocation(parent, Gravity.CENTER, 0, 0);
    }

    public void release() {
        mActivity = null;
        dismiss();
    }
}