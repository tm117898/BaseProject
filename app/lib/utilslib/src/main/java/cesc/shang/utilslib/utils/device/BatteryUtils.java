package cesc.shang.utilslib.utils.device;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;

/**
 * Created by shanghaolongteng on 2016/7/14.
 */
public class BatteryUtils {
    public BatteryUtils() {

    }

    /**
     * 注册电池相关广播
     *
     * @param context  上下文
     * @param receiver 变化receiver
     */
    public void register(Context context, BroadcastReceiver receiver) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED); //只能动态注册
        filter.addAction(Intent.ACTION_BATTERY_LOW);
        filter.addAction(Intent.ACTION_BATTERY_OKAY);
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);

        context.registerReceiver(receiver, filter);

        changeBatteryReceiver(true, context, receiver.getClass());
    }

    /**
     * 反注册电池相关广播
     *
     * @param context  上下文
     * @param receiver 变化receiver
     */
    public void unregister(Context context, BroadcastReceiver receiver) {
        context.unregisterReceiver(receiver);

        changeBatteryReceiver(false, context, receiver.getClass());
    }

    /**
     * 改变电池广播状态
     *
     * @param enable        是否接收消息
     * @param context       上下文
     * @param receiverClass 接收消息Receiver的Class
     */
    public void changeBatteryReceiver(boolean enable, Context context, Class<?> receiverClass) {
        int state;
        if (enable) {
            state = PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
        } else {
            state = PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        }

        PackageManager manager = context.getPackageManager();
        ComponentName cn = new ComponentName(context, receiverClass);

        manager.setComponentEnabledSetting(cn, state, PackageManager.DONT_KILL_APP);
    }
}
