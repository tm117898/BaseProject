package cesc.shang.demo.examples.notify;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

import butterknife.OnClick;
import cesc.shang.demo.R;
import cesc.shang.demo.base.DemoBaseActivity;
import cesc.shang.demo.examples.main.MainActivity;
import cesc.shang.utilslib.utils.util.DateTimeUtils;
import cesc.shang.utilslib.utils.widget.ActivityUtils;
import cesc.shang.utilslib.utils.widget.NotifyUtils;

/**
 * Created by shanghaolongteng on 2016/8/10.
 */
public class NotifyActivity extends DemoBaseActivity {
    @Override
    public int getContentViewId() {
        return R.layout.notify_activity_layout;
    }

    @Override
    public void setupView() {
        super.setupView();
        getActivityUtils().disableFullScreen(this);
    }

    private ActivityUtils getActivityUtils() {
        return getUtilsManager().getActivityUtils();
    }

    @Override
    public void initData() {
        Intent intent = new Intent(this, MainActivity.class);
        mIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        mCustomView = new RemoteViews(getPackageName(), R.layout.notify_custom_layout);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getActivityUtils().enableFullScreen(this);
    }

    private PendingIntent mIntent = null;
    private RemoteViews mCustomView = null;
    private final int SYSTEM_NOTIFY_ID = 1122, CUSTOM_NOTIFY_ID = 3344;

    @OnClick({R.id.show_system_notify, R.id.hide_system_notify, R.id.show_custom_notify, R.id.hide_custom_notify})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.show_system_notify://String contentTitle, String , int number, PendingIntent intent, int flags
                getNotifyUtils().notify(this, SYSTEM_NOTIFY_ID, R.mipmap.ic_launcher, "tickerText", "contentTitle", "setContentText", 11, mIntent, Notification.FLAG_AUTO_CANCEL);
                break;
            case R.id.hide_system_notify:
                getNotifyUtils().cancel(this, SYSTEM_NOTIFY_ID);
                break;
            case R.id.show_custom_notify:
                mCustomView.setTextViewText(R.id.hour_text, getDateTimeUtils().getTodayString());
                mCustomView.setTextViewText(R.id.minute_text, getDateTimeUtils().getTimeString());
                getNotifyUtils().notify(this, CUSTOM_NOTIFY_ID, R.mipmap.ic_launcher, "tickerText", mCustomView, mIntent, Notification.FLAG_AUTO_CANCEL);
                break;
            case R.id.hide_custom_notify:
                getNotifyUtils().cancel(this, CUSTOM_NOTIFY_ID);
                break;
        }
    }

    private DateTimeUtils getDateTimeUtils() {
        return getUtilsManager().getDateTimeUtils();
    }

    private NotifyUtils getNotifyUtils() {
        return getUtilsManager().getNotifyUtils();
    }
}
