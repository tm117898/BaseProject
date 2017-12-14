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

    /**
     * 获取ActivityManager
     *
     * @param context 上下文
     * @return ActivityManager
     */
    private ActivityManager getActivityService(Context context) {
        return (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    }

    /**
     * 获取ActivityManager中MemoryInfo
     *
     * @param context 上下文
     * @return ActivityManager.MemoryInfo
     */
    public ActivityManager.MemoryInfo getMemoryInfo(Context context) {
        ActivityManager am = getActivityService(context);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(info);
        return info;
    }

    /**
     * 获取APP最大使用内存
     *
     * @param context 上下文
     * @return （M）
     */
    public int getMemoryClass(Context context) {
        ActivityManager am = getActivityService(context);
        return am.getMemoryClass();
    }

    /**
     * 获取APP最大使用内存
     * AndroidManifest.xml中的application标签加上android:largeHeap="true"
     *
     * @param context 上下文
     * @return （M）
     */
    public int getLargeMemoryClass(Context context) {
        ActivityManager am = getActivityService(context);
        return am.getLargeMemoryClass();
    }

    /**
     * 生成hprof文件
     *
     * @param fileName 文件绝对路径
     */
    public void dumpHprofData(String fileName) {
        try {
            Debug.dumpHprofData(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Debug中MemoryInfo
     *
     * @return Debug.MemoryInfo
     */
    public Debug.MemoryInfo getMemoryInfo() {
        Debug.MemoryInfo info = new Debug.MemoryInfo();
        Debug.getMemoryInfo(info);
        return info;
    }

    /**
     * 得到Native堆的内存大概情况
     *
     * @return 数据单位为字节
     */
    public long getNativeHeapAllocatedSize() {
        return Debug.getNativeHeapAllocatedSize();
    }

    /**
     * 是当前进程navtive堆本身总的内存大小
     *
     * @return 数据单位为字节
     */
    public long getNativeHeapSize() {
        return Debug.getNativeHeapSize();
    }

    /**
     * 当前进程navtive堆中已经剩余的内存大小
     *
     * @return 数据单位为字节
     */
    public long getNativeHeapFreeSize() {
        return Debug.getNativeHeapFreeSize();
    }
}
