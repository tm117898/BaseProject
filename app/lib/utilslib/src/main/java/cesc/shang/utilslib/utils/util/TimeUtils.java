package cesc.shang.utilslib.utils.util;

import android.os.Debug;
import android.os.SystemClock;

/**
 * Created by shanghaolongteng on 2016/7/14.
 */
public class TimeUtils {
    public TimeUtils() {
    }

    /**
     * 获取当前时间
     *
     * @return 毫秒
     */
    public long getCurTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 这个时间没有意思，只适用于测试时间间隔
     *
     * @return 毫秒
     */
    public long getNanoTime() {
        return System.nanoTime();
    }

    /**
     * 测量当前线程中花费时间时，结果更为精确
     *
     * @return 毫秒
     */
    public long getThreadCpuTimeNanos() {
        return Debug.threadCpuTimeNanos();
    }

    /**
     * 返在当前线程运行的毫秒数
     *
     * @return 毫秒
     */
    public long curThreadTimeMillis() {
        return SystemClock.currentThreadTimeMillis();
    }

    /**
     * 返回系统启动到现在的纳秒数，包含休眠时间。
     *
     * @return 纳秒
     */
    public long elapsedRealtime() {
        return SystemClock.elapsedRealtime();
    }

    /**
     * 返回系统启动到现在的毫秒级时间，不包含休眠时间
     *
     * @return 毫秒
     */
    public long uptimeMillis() {
        return SystemClock.uptimeMillis();
    }
}
