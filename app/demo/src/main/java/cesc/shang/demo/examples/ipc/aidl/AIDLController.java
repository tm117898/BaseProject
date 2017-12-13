package cesc.shang.demo.examples.ipc.aidl;

import android.content.ComponentName;
import android.os.IBinder;
import android.os.RemoteException;

import cesc.shang.baselib.base.activity.BaseActivity;
import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.base.service.ServiceBindHelper;
import cesc.shang.baselib.support.manager.base.BaseController;
import cesc.shang.utilslib.utils.debug.LogUtils;
import cesc.shang.utilslib.utils.util.ThreadUtils;

/**
 * Created by shanghaolongteng on 2017/7/16.
 */

public class AIDLController extends BaseController {
    private ServiceBindHelper mHelper;
    private AidlInterface mAIDL = null;
    private LogUtils mLog;

    public AIDLController(BaseApplication app) {
        super(app);
        mLog = getUtilsManager().getLogUtils(this.getClass().getSimpleName());
    }

    @Override
    public void onDestroy() {

    }

    public void onActivityCreate(BaseActivity activity) {
        mHelper = new ServiceBindHelper(activity) {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                mAIDL = AidlInterface.Stub.asInterface(iBinder);
                try {
                    printProgressAndThread();
                    mAIDL.registerCallBack(mCallBack);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                try {
                    printProgressAndThread();
                    mAIDL.unRegisterCallBack(mCallBack);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private ThreadUtils getThreadUtils() {
        return getUtilsManager().getThreadUtils();
    }

    private void printProgressAndThread() {
        getThreadUtils().printProgressAndThread(mLog);
    }

    public void onActivityDestroy() {
        mHelper.onDestroy();
        mHelper = null;
    }

    AIDLCallBack mCallBack = new AIDLCallBack.Stub() {
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
        public void callBack(AIDLEntity entity) throws RemoteException {
            printProgressAndThread();
            mLog.i("callBack , entity : ", entity);
        }
    };

    public void bindService() {
        mHelper.bindService(AIDLService.class);
    }

    public void sendEntity() {
        AIDLEntity entity = new AIDLEntity();
        entity.setMale(false);
        entity.setAge(18);
        entity.setName("Doris");

        if (mAIDL != null) {
            try {
                printProgressAndThread();
                mAIDL.sendEntity(entity);//此方法会被挂起，直到服务端方法执行结束
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
