package cesc.shang.baselib.base.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.support.manager.ControllerManager;
import cesc.shang.baselib.support.manager.HandlerManager;
import cesc.shang.baselib.support.manager.UtilsManager;
import cesc.shang.utilslib.utils.debug.LogUtils;

/**
 * Created by Cesc Shang on 2017/7/17.
 */

public abstract class BaseFragment extends Fragment implements cesc.shang.baselib.support.IContextSupport {
    public static final int CONTENT_VIEW_LAYOUT_INVALID_ID = 0;

    protected LogUtils mLog;
    protected Unbinder mButterKnife;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mLog = getUtilsManager().getLogUtils(this.getClass().getSimpleName());
        mLog.i("onCreateView()");

        View view = setContentView(inflater, container);
        mButterKnife = ButterKnife.bind(this, view);

        setupView(view);
        initData();
        return view;
    }

    /**
     * 设置Activity的ContentView
     *
     * @param inflater  LayoutInflater
     * @param container 容器
     * @return ContentView
     */
    private View setContentView(LayoutInflater inflater, ViewGroup container) {
        View contentView = null;
        do {
            int id = getContentViewId();
            if (id > CONTENT_VIEW_LAYOUT_INVALID_ID) {
                contentView = inflater.inflate(id, container, false);
                break;
            }

            View view = getContentView();
            if (view != null) {
                contentView = view;
                break;
            }

            mLog.i("setContentView() , ContentView is null");
        } while (false);

        return contentView;
    }

    /**
     * 获取Activity布局Id
     *
     * @return layoutId
     */
    protected abstract int getContentViewId();

    /**
     * 当{@link #getContentViewId()}返回{@link #CONTENT_VIEW_LAYOUT_INVALID_ID}时，
     * 可重写此方法返回View。
     *
     * @return ContentView
     */
    protected View getContentView() {
        return null;
    }

    /**
     * 处理View相关操作，如View.setOnClickLister() or AdapterView.setAdapter() or ...
     *
     * @param rootView Fragment根布局
     */
    protected void setupView(View rootView) {
    }

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLog.i("onActivityCreated()");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLog.i("create()");
    }

    @Override
    public void onResume() {
        super.onResume();
        mLog.i("onResume()");
    }

    @Override
    public void onStart() {
        super.onStart();
        mLog.i("onRestart()");
    }

    @Override
    public void onPause() {
        super.onPause();
        mLog.i("onPause()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLog.i("destroy()");
        mButterKnife.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mLog.i("onActivityResult() , requestCode : ", requestCode, " , resultCode : ", resultCode, " , data : ", data);
    }

    @Override
    public BaseApplication getApp() {
        return (BaseApplication) getContext().getApplicationContext();
    }

    @Override
    public ControllerManager getControllerManager() {
        return getApp().getControllerManager();
    }

    @Override
    public HandlerManager getHandlerManager() {
        return getApp().getHandlerManager();
    }

    @Override
    public UtilsManager getUtilsManager() {
        return getApp().getUtilsManager();
    }


    /**
     * 替换当前显示Fragment
     *
     * @param contentLayoutId Fragment的容器Id
     * @param fragment        要显示的Fragment
     */
    public void replaceFragment(int contentLayoutId, BaseFragment fragment) {
        replaceFragment(contentLayoutId, fragment, false);
    }

    /**
     * 替换当前显示Fragment
     *
     * @param contentLayoutId Fragment的容器Id
     * @param fragment        要显示的Fragment
     * @param isToBackStack   是都将Fragment加入回退栈
     */

    public void replaceFragment(int contentLayoutId, BaseFragment fragment, boolean isToBackStack) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(contentLayoutId, fragment);
        if (isToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commitAllowingStateLoss();
    }
}
