package cesc.shang.utilslib.utils.debug;

import android.app.ActivityManager;
import android.content.Context;

/**
 * Created by shanghaolongteng on 2016/8/12.
 */
public class ProcessUtils {
    public ProcessUtils() {
    }

    /**
     * 获取当前进程名称
     *
     * @param context 上下文，用于获取ActivityManager
     * @return 当前进程名称
     */
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
