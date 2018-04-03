package cesc.shang.demo.examples.ipc.binderconnectionpool;

import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.support.context.IContextSupport;
import cesc.shang.baselib.support.manager.base.BaseController;
import cesc.shang.utilslib.utils.debug.LogUtils;
import cesc.shang.utilslib.utils.util.ThreadUtils;

/**
 * Created by shanghaolongteng on 2017/7/16.
 */

public class BinderController extends BaseController {
    public static final String BINDER_THREAD_NAME = "BinderActivity-thread";
    private LogUtils mLog;
    private Handler mWork;
    private BinderTestFirst mFirst;
    private BinderTestSecond mSecond;
    private BinderPool mPool;

    public BinderController(BaseApplication app) {
        super(app);
        mLog = getUtilsManager().getLogUtils(this.getClass().getSimpleName());
    }

    @Override
    public void onDestroy() {
        getThreadUtils().quitHandlerThread(mWork);
    }

    public void onActivityCreate(final IContextSupport support) {
        printProgressAndThread();
        mWork = getThreadUtils().getHandlerThread(BINDER_THREAD_NAME);
        mWork.post(new Runnable() {
            @Override
            public void run() {
                mPool = new BinderPool(support);
            }
        });
    }

    private void printProgressAndThread() {
        getThreadUtils().printProgressAndThread(mLog);
    }

    public void onActivityDestroy() {
        getThreadUtils().quitHandlerThread(mWork);
        mPool.onDestroy();
        mWork = null;
        mFirst = null;
        mSecond = null;
    }

    private ThreadUtils getThreadUtils() {
        return getUtilsManager().getThreadUtils();
    }

    public void getFirstBinder() {
        mWork.post(new Runnable() {
            @Override
            public void run() {
                printProgressAndThread();
                try {
                    IBinder binder = mPool.queryBinder(BinderService.TEST_FIRST_CODE);
                    mFirst = BinderTestFirst.Stub.asInterface(binder);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void callFirstBinderMethod() {
        mWork.post(new Runnable() {
            @Override
            public void run() {
                printProgressAndThread();
                try {
                    String s = mFirst.testFirst("sssss");
                    mLog.i("call_first_binder_method : " + s);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getSecondBinder() {
        mWork.post(new Runnable() {
            @Override
            public void run() {
                printProgressAndThread();
                try {
                    IBinder binder = mPool.queryBinder(BinderService.TEST_SECOND_CODE);
                    mSecond = BinderTestSecond.Stub.asInterface(binder);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void callSecondBinderMethod() {
        mWork.post(new Runnable() {
            @Override
            public void run() {
                printProgressAndThread();
                try {
                    int i = mSecond.testSecond(117);
                    mLog.i("call_second_binder_method : " + i);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
