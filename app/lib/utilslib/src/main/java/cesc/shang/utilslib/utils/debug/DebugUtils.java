package cesc.shang.utilslib.utils.debug;

import android.os.Debug;

/**
 * Created by shanghaolongteng on 2016/7/14.
 */
public class DebugUtils {
    public DebugUtils() {
    }

    public void startMethodTracing() {
        Debug.startMethodTracing();
    }

    public void startMethodTracing(String traceName) {
        Debug.startMethodTracing(traceName);
    }

    public void startMethodTracing(String traceName, int bufferSize) {
        Debug.startMethodTracing(traceName, bufferSize);
    }

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
