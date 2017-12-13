package cesc.shang.utilslib.utils.debug;

import android.os.StrictMode;

/**
 * Created by shanghaolongteng on 2016/7/14.
 */
public class StrictModeUtils {
    public StrictModeUtils() {
    }

    /**
     * 开启严格模式
     */
    public void enableStrictMode() {
        setThreadPolicy();
        setVmPolicy();
    }

    /**
     * 设置线程检测模式
     */
    public void setThreadPolicy() {
        StrictMode.ThreadPolicy.Builder builder = new StrictMode.ThreadPolicy.Builder();
        builder.detectAll();
//        builder.detectCustomSlowCalls();
//        builder.detectDiskReads();
//        builder.detectDiskWrites();
//        builder.detectNetwork();
        builder.penaltyLog();
        builder.penaltyFlashScreen();
        StrictMode.setThreadPolicy(builder.build());
    }

    /**
     * 设置虚拟机检测模式
     */
    public void setVmPolicy() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        builder.detectAll();
//        builder.detectLeakedSqlLiteObjects();
//        builder.detectLeakedClosableObjects();
        // 检测实例数量
//        try {
//            builder.setClassInstanceLimit(Class.forName("com.apress.proandroid.SomeClass"), CLASS_INSTANCE_LIMIT);
//        } catch (ClassNotFoundException e) {
////            e.printStackTrace();
//        }
        builder.penaltyLog();
        StrictMode.setVmPolicy(builder.build());
    }
}
