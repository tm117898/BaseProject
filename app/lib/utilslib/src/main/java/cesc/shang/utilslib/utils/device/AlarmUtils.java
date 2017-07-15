package cesc.shang.utilslib.utils.device;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;

/**
 * Created by shanghaolongteng on 2016/7/14.
 */
public class AlarmUtils {
    public AlarmUtils() {

    }

    public void registerAlarmByBroadcast(Context context, Class<?> serviceClass) {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, getIntent(context, serviceClass), 0);

        registerAlarm(context, pendingIntent);
    }

    public void registerAlarmByActivity(Context context, Class<?> serviceClass) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, getIntent(context, serviceClass), 0);

        registerAlarm(context, pendingIntent);
    }

    public void registerAlarmByService(Context context, Class<?> serviceClass) {
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, getIntent(context, serviceClass), 0);

        registerAlarm(context, pendingIntent);
    }

    private Intent getIntent(Context context, Class<?> serviceClass) {
        return new Intent(context, serviceClass);
    }

    private void registerAlarm(Context context, PendingIntent pendingIntent) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        long interval = DateUtils.HOUR_IN_MILLIS * 1;
        long firstInterval = DateUtils.MINUTE_IN_MILLIS * 30;
        manager.setRepeating(AlarmManager.RTC_WAKEUP, firstInterval, interval, pendingIntent);
    }

    public void unregisterAlarm(Context context, PendingIntent pendingIntent) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
    }

    private void registerInexactAlarm(Context context, PendingIntent pendingIntent) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        long interval = AlarmManager.INTERVAL_HOUR;
        long firstInterval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstInterval, interval, pendingIntent);
    }
}
