package cesc.shang.baselib.base.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import cesc.shang.baselib.support.BaseContextSupport;
import cesc.shang.utilslib.utils.debug.LogUtils;

/**
 * Created by shanghaolongteng on 2017/7/16.
 */

public abstract class ServiceBindBaseHelper implements ServiceConnection {
    protected boolean isBind = false;
    protected BaseContextSupport mSupport;
    protected LogUtils mLog;

    public ServiceBindBaseHelper(BaseContextSupport support) {
        onCreate(support);
    }

    public void onCreate(BaseContextSupport support) {
        mSupport = support;
        mLog = support.getUtilsManager().getLogUtils(ServiceBindBaseHelper.class.getSimpleName());
    }

    public void bindService(Class<?> service) {
        mLog.i("bindService , service : ", service, " , isBind : ", isBind);
        if (!isBind) {
            Context context = mSupport.getContext();
            Intent intent = new Intent(context, service);
            context.bindService(intent, mConn, Context.BIND_AUTO_CREATE);
        }
    }

    public void unbindService() {
        mLog.i("unbindService , isBind : ", isBind);
        if (isBind) {
            Context context = mSupport.getContext();
            context.unbindService(mConn);
        }
    }

    public void onDestroy() {
        mLog.i("onDestroy");
        unbindService();
        mSupport = null;
    }

    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isBind = true;
            mLog.i("onServiceConnected , name : ", name);
            ServiceBindBaseHelper.this.onServiceConnected(name, service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mLog.i("onServiceDisconnected , name : ", name);
            ServiceBindBaseHelper.this.onServiceDisconnected(name);
            isBind = false;
        }
    };
}
