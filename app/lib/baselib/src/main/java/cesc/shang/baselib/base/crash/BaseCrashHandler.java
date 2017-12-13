package cesc.shang.baselib.base.crash;

import android.os.Process;

import cesc.shang.baselib.support.IContextSupport;
import cesc.shang.utilslib.utils.debug.LogUtils;

/**
 * Created by shanghaolongteng on 2016/7/17.
 */
public class BaseCrashHandler implements Thread.UncaughtExceptionHandler {
    protected LogUtils mLog;

    public BaseCrashHandler(IContextSupport support) {
        mLog = support.getUtilsManager().getLogUtils(getClass().getSimpleName());
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        mLog.e("BaseCrashHandler , uncaughtException");
        mLog.e(ex);

        disposeException(thread, ex);

        Process.killProcess(Process.myPid());
    }

    public void disposeException(Thread thread, Throwable ex) {
    }
}
