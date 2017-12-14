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
    public static final int INVALID_RESULT_INT = -1;

    /**
     * 创建系统样式通知
     *
     * @param context        上下文
     * @param iconId         图标Drawable的id
     * @param tickerText     通知栏上面显示的文字
     * @param contentTitle   通知标题
     * @param setContentText 通知内容
     * @param number         显示在小图标左侧的数字
     * @param intent         点击通知之后需要跳转的页面
     * @param flags          {@link Notification#FLAG_AUTO_CANCEL}
     * @return
     */
    public Notification createSystemNotify(Context context, int iconId, String tickerText, String contentTitle,
                                           String setContentText, int number, PendingIntent intent, int flags) {
        NotificationCompat.Builder build = new NotificationCompat.Builder(context);
        if (iconId > 0) {
            build.setSmallIcon(iconId);
        }
        if (!TextUtils.isEmpty(tickerText)) {
            build.setTicker(tickerText);
        }
        if (!TextUtils.isEmpty(contentTitle)) {
            build.setContentTitle(contentTitle);
        }
        if (!TextUtils.isEmpty(setContentText)) {
            build.setContentText(setContentText);
        }
        if (number > 0) {
            build.setNumber(number);
        }
        build.setWhen(System.currentTimeMillis());
        build.setContentIntent(intent);

        Notification notify = build(build);
        notify.flags = flags; //Notification.FLAG_AUTO_CANCEL;

        return notify;
    }

    /**
     * Builder转换Notification
     *
     * @param build {@link #createSystemNotify}
     * @return Notification
     */
    private Notification build(NotificationCompat.Builder build) {
        Notification notify;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notify = build.build();
        } else {
            notify = build.getNotification();
        }
        return notify;
    }

    /**
     * 显示系统通知
     *
     * @param context        上下文
     * @param id             通知的id，用于取消
     * @param iconId         图标Drawable的id
     * @param tickerText     通知栏上面显示的文字
     * @param contentTitle   通知标题
     * @param setContentText 通知内容
     * @param number         显示在小图标左侧的数字
     * @param intent         点击通知之后需要跳转的页面
     * @param flags          {@link Notification#FLAG_AUTO_CANCEL}
     * @return 通知的id，用于取消
     */
    public int notify(Context context, int id, int iconId, String tickerText, String contentTitle,
                      String setContentText, int number, PendingIntent intent, int flags) {
        Notification notification =
                createSystemNotify(context, iconId, tickerText, contentTitle, setContentText, number, intent, flags);
        getNotificationManager(context).notify(id, notification);
        return id;
    }

    /**
     * 显示自定义通知
     *
     * @param context    上下文
     * @param id         通知的id，用于取消
     * @param iconId     图标Drawable的id
     * @param tickerText 通知栏上面显示的文字
     * @param content    自定义View
     * @param intent     点击通知之后需要跳转的页面
     * @param flags      {@link Notification#FLAG_AUTO_CANCEL}
     * @return 通知的id，用于取消
     */
    public int notify(Context context, int id, int iconId, String tickerText, RemoteViews content, PendingIntent intent,
                      int flags) {
        Notification notification = createSystemNotify(context, iconId, tickerText, null, null,
                INVALID_RESULT_INT, intent, flags);
        notification.contentView = content;
        getNotificationManager(context).notify(id, notification);
        return id;
    }

    /**
     * 获取NotificationManager
     *
     * @param context 上下文
     * @return NotificationManager
     */
    public NotificationManager getNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /**
     * 取消通知
     *
     * @param context 上下文
     * @param id      要取消通知的id
     */
    public void cancel(Context context, int id) {
        getNotificationManager(context).cancel(id);
    }
}
