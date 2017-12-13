package cesc.shang.utilslib.utils.debug;

import android.os.Debug;

/**
 * Created by shanghaolongteng on 2016/7/14.
 */
public class DebugUtils {
    public DebugUtils() {
    }

    /**
     * 开始函数执行时间跟踪
     */
    public void startMethodTracing() {
        Debug.startMethodTracing();
    }

    /**
     * 开始函数执行时间跟踪
     *
     * @param traceName 生成文件名称
     */
    public void startMethodTracing(String traceName) {
        Debug.startMethodTracing(traceName);
    }

    /**
     * 开始函数执行时间跟踪
     *
     * @param traceName  生成文件名称
     * @param bufferSize 生成文件大小，默认8M
     */
    public void startMethodTracing(String traceName, int bufferSize) {
        Debug.startMethodTracing(traceName, bufferSize);
    }

    /**
     * 开始函数执行时间跟踪
     *
     * @param traceName  生成文件名称
     * @param bufferSize 生成文件大小，默认8M
     * @param flags      {@link Debug#TRACE_COUNT_ALLOCS}
     */
    public void startMethodTracing(String traceName, int bufferSize, int flags) {
        Debug.startMethodTracing(traceName, bufferSize, flags);
    }

    /**
     * 查看命令：traceview XXX.trace
     */
    public void stopMethodTracing() {
        Debug.stopMethodTracing();
    }

}
