package cesc.shang.baselib.base.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.base.fragment.BaseFragment;
import cesc.shang.baselib.support.manager.ControllerManager;
import cesc.shang.baselib.support.manager.HandlerManager;
import cesc.shang.baselib.support.manager.UtilsManager;
import cesc.shang.utilslib.utils.debug.LogUtils;

/**
 * Created by shanghaolongteng on 2016/7/14.
 */
public abstract class BaseActivity extends FragmentActivity implements cesc.shang.baselib.support.IContextSupport {
    protected LogUtils mLog;
    protected Unbinder mButterKnife;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        mLog = getUtilsManager().getLogUtils(this.getClass().getSimpleName());
        mLog.i("create()");
        mButterKnife = ButterKnife.bind(this);
        setupView();
        initData();
    }

    public abstract int getContentViewId();

    public void setupView() {
    }

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

    public void replaceFragment(int contentLayoutId, BaseFragment fragment) {
        replaceFragment(contentLayoutId, fragment, false);
    }

    public void replaceFragment(int contentLayoutId, BaseFragment fragment, boolean isToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(contentLayoutId, fragment);
        if (isToBackStack)
            transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(int messageId) {
        Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show();
    }
}
