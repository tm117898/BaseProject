package cesc.shang.baselib.base.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import cesc.shang.baselib.support.context.IContextSupport;
import cesc.shang.utilslib.utils.debug.LogUtils;

/**
 * Created by shanghaolongteng on 2017/7/16.
 */

public abstract class ServiceBindHelper implements ServiceConnection {
    protected boolean isBind = false;
    protected IContextSupport mSupport;
    protected LogUtils mLog;

    public ServiceBindHelper(IContextSupport support) {
        onCreate(support);
    }

    /**
     * 开始创建对象
     *
     * @param support {@link IContextSupport}
     */
    public void onCreate(IContextSupport support) {
        mSupport = support;
        mLog = support.getUtilsManager().getLogUtils(ServiceBindHelper.class.getSimpleName());
    }

    /**
     * 开始绑定Service
     *
     * @param service 要绑定的service.class
     */
    public void bindService(Class<?> service) {
        mLog.i("bindService , service : ", service, " , isBind : ", isBind);
        if (!isBind) {
            Context context = mSupport.getContext();
            Intent intent = new Intent(context, service);
            context.bindService(intent, mConn, Context.BIND_AUTO_CREATE);
        }
    }

    /**
     * 解绑service
     */
    public void unbindService() {
        mLog.i("unbindService , isBind : ", isBind);
        if (isBind) {
            Context context = mSupport.getContext();
            context.unbindService(mConn);
        }
    }

    /**
     * 销毁、释放资源
     */
    public void onDestroy() {
        mLog.i("destroy");
        unbindService();
        mSupport = null;
    }

    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            serviceConnected(name, service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceDisconnected(name);
        }
    };

    /**
     * 连接建立
     *
     * @param name    已绑定service的ComponentName
     * @param service 已绑定service的Binder对象
     */
    protected void serviceConnected(ComponentName name, IBinder service) {
        isBind = true;
        mLog.i("onServiceConnected , name : ", name);
        ServiceBindHelper.this.onServiceConnected(name, service);
    }

    /**
     * 连接断开
     *
     * @param name 已绑定service的ComponentName
     */
    protected void serviceDisconnected(ComponentName name) {
        mLog.i("onServiceDisconnected , name : ", name);
        ServiceBindHelper.this.onServiceDisconnected(name);
        isBind = false;
    }
}
