package cesc.shang.utilslib.utils.widget;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.RemoteViews;

/**
 * Created by shanghaolongteng on 2016/8/10.
 */
public class NotifyUtils {
    public static Notification createSystemNotify(Context context, int iconId, String tickerText, String contentTitle, String setContentText, int number,
                                                  PendingIntent intent, int flags) {
        NotificationCompat.Builder build = new NotificationCompat.Builder(context);
        if (iconId > 0)
            build.setSmallIcon(iconId);
        if (!TextUtils.isEmpty(tickerText))
            build.setTicker(tickerText);
        build.setWhen(System.currentTimeMillis());
        if (!TextUtils.isEmpty(contentTitle))
            build.setContentTitle(contentTitle);
        if (!TextUtils.isEmpty(setContentText))
            build.setContentText(setContentText);
        build.setContentIntent(intent);
        if (number > 0)
            build.setNumber(number);

        Notification notify = build(build);
        notify.flags = flags;//Notification.FLAG_AUTO_CANCEL;

        return notify;
    }

    private static Notification build(NotificationCompat.Builder build) {
        Notification notify;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notify = build.build();
        } else {
            notify = build.getNotification();
        }
        return notify;
    }

    public static int notify(Context context, int id,
                             int iconId, String tickerText, String contentTitle, String setContentText, int number, PendingIntent intent, int flags) {
        Notification notification = createSystemNotify(context, iconId, tickerText, contentTitle, setContentText, number, intent, flags);
        getNotificationManager(context).notify(id, notification);
        return id;
    }

    public static int notify(Context context, int id, int iconId, String tickerText, RemoteViews content, PendingIntent intent, int flags) {
        Notification notification = createSystemNotify(context, iconId, tickerText, null, null, -1, intent, flags);
        notification.contentView = content;
        getNotificationManager(context).notify(id, notification);
        return id;
    }

    public static NotificationManager getNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public static void cancel(Context context, int id) {
        getNotificationManager(context).cancel(id);
    }
}
