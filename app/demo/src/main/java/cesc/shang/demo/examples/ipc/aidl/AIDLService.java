package cesc.shang.demo.examples.ipc.aidl;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.text.TextUtils;

import cesc.shang.baselib.base.service.BaseService;
import cesc.shang.utilslib.utils.util.ThreadUtils;

/**
 * Created by shanghaolongteng on 2016/7/31.
 */
public class AIDLService extends BaseService {
    private final String SERVICE_PERMISSION = "cesc.shang.demo.examples.ipc.aidl.permission.access";
    private RemoteCallbackList<AIDLCallBack> mCallBacks = new RemoteCallbackList<>();

    private AidlInterface.Stub mBinder = new AidlInterface.Stub() {
        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {//每次通信都会调用……
            getThreadUtils().printProgressAndThread(mLog);
            int check = checkCallingOrSelfPermission(SERVICE_PERMISSION);
            if (check == PackageManager.PERMISSION_DENIED) {//排除没有权限的
                mLog.i("mBinder , onTransact , 没有权限");
                return false;
            } else {
                String[] packages = getPackageManager().getPackagesForUid(getCallingUid());
                String packageName = null;
                if (packages != null && packages.length > 0) {
                    packageName = packages[0];
                }
                if (!TextUtils.isEmpty(packageName) && packageName.startsWith("XXX.XXX")) {//排除指定应用
                    mLog.i("mBinder , onTransact , 指定应用");
                    return false;
                }
            }
            mLog.i("mBinder , onTransact , 鉴权成功");
            return super.onTransact(code, data, reply, flags);
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            mLog.i("anInt : ", anInt,
                    "aLong : ", aLong,
                    "aBoolean : ", aBoolean,
                    "aFloat : ", aFloat,
                    "aDouble : ", aDouble,
                    "aString : ", aString);
        }

        @Override
        public void sendEntity(AIDLEntity entity) throws RemoteException {//此方法运行在Binder线程池中，无需另开线程
            getThreadUtils().printProgressAndThread(mLog);
            mLog.i("sendEntity , entity : " + entity);
            entity.setAge(27);
            entity.setName("Cesc Shang");
            entity.setMale(true);

            int size = mCallBacks.beginBroadcast();
            for (int i = 0; i < size; i++) {
                AIDLCallBack callBack = mCallBacks.getBroadcastItem(i);
                callBack.callBack(entity);
            }
            mCallBacks.finishBroadcast();
        }

        @Override
        public void registerCallBack(AIDLCallBack callBack) throws RemoteException {//此方法运行在Binder线程池中，无需另开线程
            mLog.i("registerCallBack");
            getThreadUtils().printProgressAndThread(mLog);
            mCallBacks.register(callBack);
        }

        @Override
        public void unRegisterCallBack(AIDLCallBack callBack) throws RemoteException {//此方法运行在Binder线程池中，无需另开线程
            mLog.i("unRegisterCallBack");
            getThreadUtils().printProgressAndThread(mLog);
            mCallBacks.unregister(callBack);
        }
    };

    private ThreadUtils getThreadUtils() {
        return getUtilsManager().getThreadUtils();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
