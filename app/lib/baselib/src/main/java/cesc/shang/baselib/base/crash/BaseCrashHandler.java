package cesc.shang.baselib.base.crash;

import android.os.Process;

import cesc.shang.baselib.support.context.IContextSupport;
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
    }

    /**
     * 处理Exception
     *
     * @param thread 当前线程
     * @param ex     当前异常
     */
    public void disposeException(Thread thread, Throwable ex) {
        Process.killProcess(Process.myPid());
    }
}
