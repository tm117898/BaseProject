package cesc.shang.demo.examples.ipc.binderconnectionpool;

import android.content.ComponentName;
import android.os.IBinder;
import android.os.RemoteException;

import cesc.shang.baselib.base.service.ServiceBindBaseHelper;
import cesc.shang.baselib.support.BaseContextSupport;
import cesc.shang.baselib.support.utils.UtilsManager;
import cesc.shang.utilslib.utils.debug.LogUtils;

/**
 * Created by shanghaolongteng on 2016/8/4.
 */
public class BinderPool {
    protected LogUtils mLog;
    private BaseContextSupport mSupport = null;
    private ServiceBindBaseHelper mHelper;
    private BinderConnectionPool mConnectionPool = null;

    public BinderPool(BaseContextSupport support) {
        mSupport = support;
        onCreate();
    }

    private synchronized void onCreate() {
        mLog = getUtilsManager().getLogUtils(this.getClass().getSimpleName());

        mHelper = new ServiceBindBaseHelper(mSupport) {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                mConnectionPool = BinderConnectionPool.Stub.asInterface(iBinder);
                try {
                    mConnectionPool.asBinder().linkToDeath(mRecipient, 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };
        mHelper.bindService(BinderService.class);
    }

    public void onDestroy() {
        mHelper.unbindService();
        mSupport = null;
    }

    private UtilsManager getUtilsManager() {
        return mSupport.getUtilsManager();
    }

    private IBinder.DeathRecipient mRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            mLog.i("mRecipient");
            mConnectionPool.asBinder().unlinkToDeath(this, 0);
            onCreate();
        }
    };

    public IBinder queryBinder(int bindCode) throws RemoteException {
        return mConnectionPool.queryBinder(bindCode);
    }
}
