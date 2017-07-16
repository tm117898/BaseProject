package cesc.shang.baselib.base.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cesc.shang.baselib.support.BaseContextSupport;
import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.support.controller.ControllerManager;
import cesc.shang.baselib.support.manager.AppManager;
import cesc.shang.baselib.support.utils.UtilsManager;
import cesc.shang.utilslib.utils.debug.LogUtils;

/**
 * Created by shanghaolongteng on 2016/7/14.
 */
public abstract class BaseActivity extends FragmentActivity implements BaseContextSupport {
    protected LogUtils mLog;
    protected Unbinder mButterKnife;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        mLog = getUtilsManager().getLogUtils(this.getClass().getSimpleName());
        mLog.i("onCreate()");
        mButterKnife = ButterKnife.bind(this);
        setupView();
        setAdapter();
        initData();
    }

    public abstract int getContentViewId();

    public void setupView() {
    }

    public void setAdapter() {
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
        mLog.i("onDestroy()");
        mButterKnife.unbind();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mLog.i("onActivityResult()");
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
    public AppManager getAppManager() {
        return getApp().getAppManager();
    }

    @Override
    public UtilsManager getUtilsManager() {
        return getApp().getUtilsManager();
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(int messageId) {
        Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show();
    }
}
