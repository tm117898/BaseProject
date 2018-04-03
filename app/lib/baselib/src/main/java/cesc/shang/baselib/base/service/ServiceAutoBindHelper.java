package cesc.shang.baselib.base.service;

import android.content.ComponentName;
import android.os.IBinder;
import android.os.RemoteException;

import cesc.shang.baselib.support.context.IContextSupport;

/**
 * Created by Cesc Shang on 2017/12/13.
 */

public abstract class ServiceAutoBindHelper extends ServiceBindHelper {
    private IBinder mServiceBinder;
    private Class<?> mServiceClass;

    public ServiceAutoBindHelper(IContextSupport support) {
        super(support);
    }

    @Override
    public void bindService(Class<?> service) {
        super.bindService(service);
        mServiceClass = service;
    }

    @Override
    protected void serviceConnected(ComponentName name, IBinder service) {
        mServiceBinder = service;
        super.serviceConnected(name, service);
        try {
            service.linkToDeath(mRecipient, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void serviceDisconnected(ComponentName name) {
        super.serviceDisconnected(name);
        mServiceBinder = null;
    }

    private IBinder.DeathRecipient mRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            onBinderDied();
        }
    };

    /**
     * 连接在服务端断开
     */
    protected void onBinderDied() {
        mLog.i("mRecipient , binderDied()");
        isBind = false;
        if (mServiceBinder != null) {
            mServiceBinder.unlinkToDeath(mRecipient, 0);
        }

        autoBindService();
    }

    /**
     * 开始重新绑定
     */
    protected void autoBindService() {
        if (mServiceClass != null) {
            mLog.i("autoBindService");
            super.bindService(mServiceClass);
        }
    }
}
