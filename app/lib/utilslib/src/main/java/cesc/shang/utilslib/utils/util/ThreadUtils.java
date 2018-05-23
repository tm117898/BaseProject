package cesc.shang.utilslib.utils.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cesc.shang.utilslib.utils.debug.LogUtils;

/**
 * Created by shanghaolongteng on 2016/7/14.
 */
public class ThreadUtils {
    public ThreadUtils() {
    }

    /**
     * 设置线程优先级
     *
     * @param tid      线程id
     * @param priority 优先级（-20~19）
     */
    public void setThreadPriority(int tid, int priority) {
        Process.setThreadPriority(tid, priority);
    }

    public void setThreadPriority(int priority) {
        Process.setThreadPriority(priority);
    }

    /**
     * 打印当前的进程id与线程id
     *
     * @param log {@link LogUtils}
     */
    public void printProgressAndThread(LogUtils log) {
        int pid = Process.myPid();
        long tid = Thread.currentThread().getId();
        log.i("printProgressAndThread , pid : ", pid, " , tid : ", tid);
    }

    /**
     * @param threadCount 核心线程数
     * @return 只有核心线程的线程池
     */
    public ExecutorService newFixedThreadPool(int threadCount) {
        return Executors.newFixedThreadPool(threadCount);
    }

    /**
     * @return 只有非核心线程的线程池
     */
    public ExecutorService newCatchedThreadPool() {
        return Executors.newCachedThreadPool();
    }

    /**
     * @param coreThreadCount 核心线程数
     * @return 核心线程数量固定，非核心无限的线程池
     */
    public ExecutorService newScheduledThreadPool(int coreThreadCount) {
        return Executors.newScheduledThreadPool(coreThreadCount);
    }

    /**
     * 获取HandlerThread
     *
     * @param threadName 线程名
     * @return Handler
     */
    public Handler getHandlerThread(String threadName) {
        HandlerThread thread = new HandlerThread(threadName);
        thread.start();
        return new Handler(thread.getLooper());
    }

    /**
     * 退出HandlerThread
     *
     * @param handler 线程关联的Handler
     */
    public void quitHandlerThread(Handler handler) {
        handler.removeCallbacksAndMessages(null);
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            handler.getLooper().quit();
        } else {
            handler.getLooper().quitSafely();
        }
    }

    public void exeRunanleInThread(String threadName, Runnable r) {
        new Thread(r, threadName).start();
    }
}
