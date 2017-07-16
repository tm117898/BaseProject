package cesc.shang.demo.examples.ipc.binderconnectionpool;

import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import cesc.shang.baselib.base.service.BaseService;

/**
 * Created by shanghaolongteng on 2016/8/4.
 */
public class BinderService extends BaseService {
    public static final int TEST_FIRST_CODE = 0;
    public static final int TEST_SECOND_CODE = 1;

    private BinderConnectionPool.Stub mPool = new BinderConnectionPool.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public IBinder queryBinder(int bindCode) throws RemoteException {
            printProgressAndThread();
            IBinder binder = null;
            switch (bindCode) {
                case TEST_FIRST_CODE:
                    binder = mFirst;
                    break;
                case TEST_SECOND_CODE:
                    binder = mSecond;
                    break;
            }
            return binder;
        }
    };

    private BinderTestFirst.Stub mFirst = new BinderTestFirst.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public String testFirst(String s) throws RemoteException {
            printProgressAndThread();
            return s += "<testFirst>";
        }
    };

    private BinderTestSecond.Stub mSecond = new BinderTestSecond.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public int testSecond(int i) throws RemoteException {
            printProgressAndThread();
            return i += 123;
        }
    };

    @Override

    public IBinder onBind(Intent intent) {
        return mPool;
    }

    private void printProgressAndThread() {
        int pid = android.os.Process.myPid();
        long tid = Thread.currentThread().getId();
        mLog.i("shlt , pid : ", pid, " , tid : ", tid);
    }
}
