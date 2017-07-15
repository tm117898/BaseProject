package cesc.shang.utilslib.utils.debug;

import android.os.StrictMode;

/**
 * Created by shanghaolongteng on 2016/7/14.
 */
public class StrictModeUtils {
    public StrictModeUtils() {
    }

    public void enableStrictMode() {
        setThreadPolicy();
        setVmPolicy();
    }

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

    public void setVmPolicy() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        builder.detectAll();
//        builder.detectLeakedSqlLiteObjects();
//        builder.detectLeakedClosableObjects();
        try {
            builder.setClassInstanceLimit(Class.forName("com.apress.proandroid.SomeClass"), 100);
        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
        }
        builder.penaltyLog();
        StrictMode.setVmPolicy(builder.build());
    }
}
