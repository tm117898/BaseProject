package cesc.shang.baselib.base.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.base.fragment.BaseFragment;
import cesc.shang.baselib.support.context.IContextSupport;
import cesc.shang.baselib.support.manager.ControllerManager;
import cesc.shang.baselib.support.manager.HandlerManager;
import cesc.shang.baselib.support.manager.UtilsManager;
import cesc.shang.utilslib.utils.debug.LogUtils;

/**
 * Created by shanghaolongteng on 2016/7/14.
 */
public abstract class BaseActivity extends FragmentActivity implements IContextSupport {
    public static final int CONTENT_VIEW_LAYOUT_INVALID_ID = 0;

    protected LogUtils mLog;
    protected Unbinder mButterKnife;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLog = getUtilsManager().getLogUtils(this.getClass().getSimpleName());
        mLog.i("create()");

        setContentView();
        mButterKnife = ButterKnife.bind(this);

        setupView();
        initData();
    }

    /**
     * 设置Activity的ContentView
     */
    private void setContentView() {
        do {
            int id = getContentViewId();
            if (id > CONTENT_VIEW_LAYOUT_INVALID_ID) {
                setContentView(id);
                break;
            }

            View view = getContentView();
            if (view != null) {
                setContentView(view);
                break;
            }

            mLog.i("setContentView() , ContentView is null");
        } while (false);
    }

    /**
     * 获取Activity布局Id
     *
     * @return layoutId
     */
    public abstract int getContentViewId();

    /**
     * 当{@link #getContentViewId()}返回{@link #CONTENT_VIEW_LAYOUT_INVALID_ID}时，
     * 可重写此方法返回View。
     *
     * @return ContentView
     */
    public View getContentView() {
        return null;
    }

    /**
     * 处理View相关操作，如View.setOnClickLister() or AdapterView.setAdapter() or ...
     */
    public void setupView() {
    }

    /**
     * 初始化数据
     */
    public void initData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLog.i("onResume()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mLog.i("onRestart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLog.i("onPause()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLog.i("destroy()");
        mButterKnife.unbind();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mLog.i("onActivityResult() , requestCode : ", requestCode, " , resultCode : ", resultCode, " , data : ", data);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mLog.i("onNewIntent() , intent : ", intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mLog.i("onSaveInstanceState()");
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public BaseApplication getApp() {
        return (BaseApplication) this.getApplication();
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
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(contentLayoutId, fragment);
        if (isToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commitAllowingStateLoss();
    }

    /**
     * 显示Toast
     *
     * @param message 要显示字符
     */
    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示Toast
     *
     * @param messageId 要显示字符id
     */
    protected void showToast(int messageId) {
        Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show();
    }
}
