package cesc.shang.utilslib.utils.device;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;

import java.io.IOException;

/**
 * Created by shanghaolongteng on 2016/7/14.
 */
public class MemoryUtils {
    public MemoryUtils() {
    }

    public ActivityManager.MemoryInfo getMemoryInfo(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(info);
        return info;
    }

    public int getMemoryClass(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        return am.getMemoryClass();
    }

    public int getLargeMemoryClass(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        return am.getLargeMemoryClass();
    }

    public void dumpHprofData(String fileName) {
        try {
            Debug.dumpHprofData(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Debug.MemoryInfo getMemoryInfo() {
        Debug.MemoryInfo info = new Debug.MemoryInfo();
        Debug.getMemoryInfo(info);
        return info;
    }

    public long getNativeHeapAllocatedSize() {
        return Debug.getNativeHeapAllocatedSize();
    }

    public long getNativeHeapSize() {
        return Debug.getNativeHeapSize();
    }
}
