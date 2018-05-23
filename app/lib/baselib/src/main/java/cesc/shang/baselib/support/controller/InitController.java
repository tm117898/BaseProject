package cesc.shang.baselib.support.controller;

import android.os.Build;
import android.os.Process;
import android.util.ArraySet;

import java.util.HashSet;
import java.util.Set;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.support.callback.ISuccessCallBack;
import cesc.shang.baselib.support.manager.base.BaseController;
import cesc.shang.utilslib.utils.util.MapSetUtils;
import cesc.shang.utilslib.utils.util.ThreadUtils;

/**
 * Created by Cesc Shang on 2018/05/23.
 */

public class InitController extends BaseController {
    public static final String INIT_THREAD_NAME = "INIT_THREAD";
    private boolean mInitSuccess = false;

    private Set<ISuccessCallBack<Void>> mInitListeners;

    public InitController(BaseApplication app) {
        super(app);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mInitListeners = new ArraySet<>();
        } else {
            mInitListeners = new HashSet<>();
        }
    }

    @Override
    public void onDestroy() {

    }

    public void init() {
        getThreadUtils().exeRunnableInThread(INIT_THREAD_NAME, new Runnable() {
            @Override
            public void run() {
                getThreadUtils().setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                initialization();
                notifyAllListeners();
            }
        });
    }

    private ThreadUtils getThreadUtils() {
        return getUtilsManager().getThreadUtils();
    }

    public void initialization() {
    }

    protected synchronized void notifyAllListeners() {
        getUtilsManager().getMapSetUtils().traversalSet(mInitListeners, new MapSetUtils.SetTraversalCallBack<ISuccessCallBack<Void>>() {
            @Override
            public boolean findObject(ISuccessCallBack<Void> value) {
                value.onSuccess(null);
                return false;
            }
        });
    }

    public synchronized void registerListener(ISuccessCallBack<Void> l) {
        if (mInitSuccess) {
            l.onSuccess(null);
        } else {
            mInitListeners.add(l);
        }
    }

    public synchronized void unregisterListener(ISuccessCallBack<Void> l) {
        mInitListeners.remove(l);
    }
}
