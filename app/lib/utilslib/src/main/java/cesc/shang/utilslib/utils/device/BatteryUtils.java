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

    public void register(Context context, BroadcastReceiver receiver) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);//只能动态注册
        filter.addAction(Intent.ACTION_BATTERY_LOW);
        filter.addAction(Intent.ACTION_BATTERY_OKAY);
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);

        context.registerReceiver(receiver, filter);

        enableBatteryReceiver(true, context, receiver.getClass());
    }

    public void unregister(Context context, BroadcastReceiver receiver) {
        context.unregisterReceiver(receiver);

        enableBatteryReceiver(false, context, receiver.getClass());
    }

    public void enableBatteryReceiver(boolean enable, Context context, Class<?> receiverClass) {
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
