package cesc.shang.utilslib.utils.debug;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

/**
 * Created by shanghaolongteng on 2016/8/12.
 */
public class ProcessUtils {
    public ProcessUtils() {
    }

    public String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : manager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
