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
    public static final int REPEAT_ALARM_INTERVAL_IN_MINUTE = 30;

    public AlarmUtils() {
    }

    /**
     * 定时向指定Broadcast发送消息
     *
     * @param context 上下文
     * @param clz     指定Broadcast
     */
    public void registerAlarmByBroadcast(Context context, Class<?> clz) {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, getIntent(context, clz), 0);
        registerAlarm(context, pendingIntent);
    }

    /**
     * 定时向指定Activity发送消息
     *
     * @param context 上下文
     * @param clz     指定Activity
     */
    public void registerAlarmByActivity(Context context, Class<?> clz) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, getIntent(context, clz), 0);
        registerAlarm(context, pendingIntent);
    }

    /**
     * 定时向指定Service发送消息
     *
     * @param context 上下文
     * @param clz     指定Service
     */
    public void registerAlarmByService(Context context, Class<?> clz) {
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, getIntent(context, clz), 0);
        registerAlarm(context, pendingIntent);
    }

    /**
     * 获取指定Intent
     *
     * @param context 上下文
     * @param clz     指定Class
     * @return 指定Intent
     */
    private Intent getIntent(Context context, Class<?> clz) {
        return new Intent(context, clz);
    }

    /**
     * 向指定PendingIntent发送定时消息
     * 时间精确，不存在时间误差
     *
     * @param context       上下文
     * @param pendingIntent 指定pendingIntent
     */
    private void registerAlarm(Context context, PendingIntent pendingIntent) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        long interval = DateUtils.HOUR_IN_MILLIS * 1;
        long firstInterval = DateUtils.MINUTE_IN_MILLIS * REPEAT_ALARM_INTERVAL_IN_MINUTE;
        manager.setRepeating(AlarmManager.RTC_WAKEUP, firstInterval, interval, pendingIntent);
    }

    /**
     * 取消向指定PendingIntent发送定时消息
     *
     * @param context       上下文
     * @param pendingIntent 指定pendingIntent
     */
    public void unregisterAlarm(Context context, PendingIntent pendingIntent) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
    }

    /**
     * 向指定PendingIntent发送定时消息
     * 时间不精确，可能存在误差
     *
     * @param context       上下文
     * @param pendingIntent 指定pendingIntent
     */
    private void registerInexactAlarm(Context context, PendingIntent pendingIntent) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        long interval = AlarmManager.INTERVAL_HOUR;
        long firstInterval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstInterval, interval, pendingIntent);
    }
}
