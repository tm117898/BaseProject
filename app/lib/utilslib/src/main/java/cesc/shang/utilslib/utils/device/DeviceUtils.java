package cesc.shang.utilslib.utils.device;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * Created by shanghaolongteng on 2016/7/14.
 */
public class DeviceUtils {
    public static final String INVALID_RESULT_STRING = "unknow";
    public static final int INVALID_RESULT_INT = -1;
    public static final int OPERATE_TYPE_CMCC = 1; //移动
    public static final int OPERATE_TYPE_CUCC = 2; //联通
    public static final int OPERATE_TYPE_CTCC = 3; //电信

    public DeviceUtils() {
    }

    /**
     * 获取手机CPU核心数
     *
     * @return CPU核心数
     */
    public int getCpuCoreCount() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * 获取手机屏幕信息
     *
     * @param activity 必须使用Activity
     * @return 屏幕信息 获取失败返回null
     * 屏幕宽度（像素）：metric.widthPixels
     * 屏幕高度（像素）：metric.heightPixels
     * 屏幕密度（0.75 / 1.0 / 1.5）：metric.density
     * 屏幕密度DPI（120 / 160 / 240）：metric.densityDpi
     */
    public DisplayMetrics getScreenInfo(Activity activity) {
        if (activity == null) {
            return null;
        }
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        activity = null;
        return metric;
    }

    /**
     * 屏幕是否是竖屏模式
     *
     * @param context 上下文
     * @return true竖屏，false横屏
     */
    public boolean screenIsPortraitOrientation(Context context) {
        Resources res = context.getResources();
        return res.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * 获取手机架构
     *
     * @return armeabi or armeabi-v7a or ...
     */
    public String getCpuAbi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return Build.SUPPORTED_ABIS[0];
        } else {
            return Build.CPU_ABI;
        }
    }

    /**
     * 获取手机短消息中心
     *
     * @param context 上下文
     * @return 短消息中心，获取失败返回{@link #INVALID_RESULT_STRING}
     */
    public String getSMSC(Context context) {
        String result = INVALID_RESULT_STRING;
        if (context != null) {
            ContentResolver cr = context.getContentResolver();
            Uri uri = Uri.parse("content://sms/");
            final String columnType = "type";
            final String columnServiceCenter = "service_center";
            String[] projection = new String[]{columnType, columnServiceCenter};

            try (Cursor c = cr.query(uri, projection, null, null, "date desc")) {
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    int typeId = c.getInt(c.getColumnIndex(columnType));
                    if (typeId == 1) {
                        String sc = c.getString(c.getColumnIndex(columnServiceCenter));
                        if (!TextUtils.isEmpty(sc)) {
                            result = sc;
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 获取SIM卡的类型
     *
     * @param imsi @see {@link #getImei(Context)}
     * @return {@link #OPERATE_TYPE_CMCC} or {@link #OPERATE_TYPE_CUCC} or
     * {@link #OPERATE_TYPE_CTCC} or {@link #INVALID_RESULT_INT}
     */
    public int getSimCardTypeByImsi(String imsi) {
        int result = INVALID_RESULT_INT;
        if (!TextUtils.isEmpty(imsi) && !INVALID_RESULT_STRING.equals(imsi)) {
            if (imsi.startsWith("46000") || imsi.startsWith("46002") || imsi.startsWith("46007")) {
                result = OPERATE_TYPE_CMCC;
            } else if (imsi.startsWith("46001")) {
                result = OPERATE_TYPE_CUCC;
            } else if (imsi.startsWith("46003")) {
                result = OPERATE_TYPE_CTCC;
            }
        }
        return result;
    }

    /**
     * 获取SIM卡IMEI
     *
     * @param context 上下文
     * @return IMEI，获取失败返回{@link #INVALID_RESULT_STRING}
     */
    public String getImei(Context context) {
        String result = INVALID_RESULT_STRING;
        int permissionState = ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);
        if (permissionState == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String imei = tm.getDeviceId();
            if (!TextUtils.isEmpty(imei)) {
                result = imei;
            }
        }
        return result;
    }

    /**
     * 当前语言环境是否未简体中文
     *
     * @return true是，false不是
     */
    public boolean isInOverSeaLang() {
        String curLanguage = "";
        curLanguage = Locale.getDefault().getLanguage();
        String country = Locale.getDefault().getCountry();

        if (!("zh".equals(curLanguage) && "CN".equals(country))) {
            return true;
        }
        return false;
    }

    /**
     * 获取当前语言
     *
     * @return 获取失败返回{@link #INVALID_RESULT_STRING}
     */
    public String getCurLanguage() {
        String curLanguage = Locale.getDefault().getLanguage();
        if (TextUtils.isEmpty(curLanguage)) {
            curLanguage = INVALID_RESULT_STRING;
        }
        return curLanguage;
    }

    /**
     * 获取当前设置国家
     *
     * @return 获取失败返回{@link #INVALID_RESULT_STRING}
     */
    public String getCurCountry() {
        String curCountry = Locale.getDefault().getCountry();
        if (TextUtils.isEmpty(curCountry)) {
            curCountry = INVALID_RESULT_STRING;
        }
        return curCountry;
    }

    /**
     * 获取当前SIM卡所在国家
     *
     * @param context 上下文
     * @return 获取失败返回{@link #INVALID_RESULT_STRING}
     */
    public String getCurCountry(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String curCountry = manager.getNetworkCountryIso();
        if (TextUtils.isEmpty(curCountry)) {
            curCountry = INVALID_RESULT_STRING;
        }
        return curCountry;
    }
}
