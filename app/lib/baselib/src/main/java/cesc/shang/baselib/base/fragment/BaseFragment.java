package cesc.shang.baselib.base.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.support.BaseContextSupport;
import cesc.shang.baselib.support.manager.AppManager;
import cesc.shang.baselib.support.utils.UtilsManager;
import cesc.shang.utilslib.utils.debug.LogUtils;

/**
 * Created by Cesc Shang on 2017/7/17.
 */

public abstract class BaseFragment extends Fragment implements BaseContextSupport {
    protected LogUtils mLog;
    protected Unbinder mButterKnife;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mLog = getUtilsManager().getLogUtils(this.getClass().getSimpleName());
        mLog.i("onCreateView()");
        View view = inflater.inflate(getContentViewId(), container, false);
        mButterKnife = ButterKnife.bind(this, view);

        setupView(view);
        setAdapter();
        initData();
        return view;
    }

    protected abstract int getContentViewId();

    protected void setupView(View rootView) {
    }

    protected void setAdapter() {
    }

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
        mLog.i("onCreate()");
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
        mLog.i("onDestroy()");
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
    public AppManager getAppManager() {
        return getApp().getAppManager();
    }

    @Override
    public UtilsManager getUtilsManager() {
        return getApp().getUtilsManager();
    }

    public void replaceFragment(int contentLayoutId, BaseFragment fragment) {
        replaceFragment(contentLayoutId, fragment, false);
    }

    public void replaceFragment(int contentLayoutId, BaseFragment fragment, boolean isToBackStack) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(contentLayoutId, fragment);
        if (isToBackStack)
            transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }
}