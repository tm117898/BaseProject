package cesc.shang.utilslib.utils.widget;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.provider.MediaStore;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by shanghaolongteng on 2016/7/15.
 */
public class ActivityUtils {
    public static final String GALLERY_INTENT_TYPE = "image/*";
    public static final String EMAIL_INTENT_TYPE = "message/rfc822";

    public ActivityUtils() {
    }

    /**
     * 设置Activity的名目方向
     *
     * @param activity    当前activity
     * @param orientation {@link ActivityInfo#screenOrientation}
     */
    public void setScreenOrientation(Activity activity, int orientation) {
        activity.setRequestedOrientation(orientation);
    }

    /**
     * 开启全屏
     *
     * @param activity 当前activity
     */
    public void enableFullScreen(Activity activity) {
        Window window = activity.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 关闭全屏
     *
     * @param activity 当前activity
     */
    public void disableFullScreen(Activity activity) {
        Window window = activity.getWindow();
        final WindowManager.LayoutParams attrs = window.getAttributes();
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.setAttributes(attrs);
        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * 打开相机app
     *
     * @param activity    当前activity
     * @param title       如果有多款应用，应用选择框的title
     * @param requestCode startActivityForResult的requestCode
     */
    public void startCameraActivityForResult(Activity activity, CharSequence title, int requestCode) {
        Intent intent = Intent.createChooser(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), title);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 打开图库app
     *
     * @param activity    当前activity
     * @param title       如果有多款应用，应用选择框的title
     * @param requestCode startActivityForResult的requestCode
     */
    public void startGalleryActivityForResult(Activity activity, CharSequence title, int requestCode) {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType(GALLERY_INTENT_TYPE);
        Intent intent = Intent.createChooser(i, title);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 打开Email App
     *
     * @param a         当前activity
     * @param title     如果有多款应用，应用选择框的title
     * @param emailAdd  email地址
     * @param subject   email标题
     * @param emailBody email内容
     */
    public void startEmailActivity(Activity a, CharSequence title, String emailAdd, String subject, String emailBody) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, emailAdd);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, emailBody);
        intent.setType(EMAIL_INTENT_TYPE);
        a.startActivity(Intent.createChooser(intent, title));
    }
}
