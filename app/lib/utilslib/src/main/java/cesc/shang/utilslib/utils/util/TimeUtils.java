package cesc.shang.utilslib.utils.util;

import android.os.Debug;
import android.os.SystemClock;

/**
 * Created by shanghaolongteng on 2016/7/14.
 */
public class TimeUtils {
    public TimeUtils() {
    }

    public long getCurTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 这个时间没有意思，只适用于测试时间间隔
     */
    public long getNanoTime() {
        return System.nanoTime();
    }

    /**
     * 测量当前线程中花费时间时，结果更为精确
     */
    public long getThreadCpuTimeNanos() {
        return Debug.threadCpuTimeNanos();
    }

    public long curThreadTimeMillis() {
        return SystemClock.currentThreadTimeMillis();
    }

    public long elapsedRealtime() {
        return SystemClock.elapsedRealtime();
    }

    public long uptimeMillis() {
        return SystemClock.uptimeMillis();
    }
}
