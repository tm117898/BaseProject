package cesc.shang.utilslib.utils.device;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * Created by shanghaolongteng on 2016/7/14.
 */
public class DeviceUtils {
    public DeviceUtils() {
    }

    public int getCpuCoreCount() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * get screen size info
     *
     * @param activity
     */
    public DisplayMetrics getScreenInfo(Activity activity) {
        if (activity == null) {
            return null;
        }
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);

        activity = null;

//        int width = metric.widthPixels; // 屏幕宽度（像素）
//        int height = metric.heightPixels; // 屏幕高度（像素）
//        float density = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5）
//        int densityDpi = metric.densityDpi; // 屏幕密度DPI（120 / 160 / 240）
        return metric;
    }

    public String getDensityDpiType(Activity activity) {
        DisplayMetrics metric = getScreenInfo(activity);
        activity = null;
        int displayHeight = metric.heightPixels;
        String sense = "unknow";// 图片的分辨率
        if (displayHeight > 640) {
            sense = "wvga";
        } else if (displayHeight <= 640 && displayHeight > 400) {
            sense = "hvga";
        } else if (displayHeight <= 400) {
            sense = "qvga";
        }
        return sense;
    }

    public String getScreenSize(Activity activity) {
        String s = "";
        DisplayMetrics metric = getScreenInfo(activity);
        activity = null;
        if (metric != null)
            s = metric.widthPixels + " * " + metric.heightPixels;
        return s;
    }

    public boolean screenIsPortraitOrientation(Context context) {
        Resources res = context.getResources();
        return res.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    public String getCpuAbi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return Build.SUPPORTED_ABIS[0];
        } else {
            return Build.CPU_ABI;
        }
    }

    /**
     * get sms center num api
     *
     * @param context
     *
     * @return
     */
    public String getSMSC(Context context) {
        if (context == null) {
            return null;
        }

        String sc = "";
        Cursor cur = null;
        final String SMS_URI_ALL = "content://sms/";
        final String PROJECTION_COLUMN_TYPE = "type";
        final String PROJECTION_COLUMN_SERVICE_CENTER = "service_center";
        try {
            ContentResolver cr = context.getContentResolver();
            String[] projection = new String[]{
                    PROJECTION_COLUMN_TYPE, PROJECTION_COLUMN_SERVICE_CENTER
            };
            Uri uri = Uri.parse(SMS_URI_ALL);
            cur = cr.query(uri, projection, null, null, "date desc");
            if (cur == null) {
                return "";
            }
            if (!cur.moveToFirst()) {
                cur.close();
                cur = null;
                return "";
            }

            int typeColumn = cur.getColumnIndex(PROJECTION_COLUMN_TYPE);
            int scColumn = cur.getColumnIndex(PROJECTION_COLUMN_SERVICE_CENTER);
            do {
                int typeId = cur.getInt(typeColumn);
                if (typeId == 1) {
                    sc = cur.getString(scColumn);
                    if (sc != null && sc.length() > 0) {
                        break;
                    }
                }
            } while (cur.moveToNext());

        } catch (Exception e) {
        } finally {
            if (cur != null && !cur.isClosed()) {
                cur.close();
                cur = null;
            }
        }

        return sc;
    }

    public final int OPERATE_TYPE_CMCC = 1; // 移动
    public final int OPERATE_TYPE_UNICOM = 2;// 联通
    public final int OPERATE_TYPE_TELECOM = 3;// 电信
    public final int OPERATE_TYPE_ONKNOWN = 0;

    /**
     * 获取sim卡的类型
     *
     * @return
     */
    public int getSimCardTypeByImsi(String imsi) {
        if (imsi != null) {
            if (imsi.startsWith("46000") || imsi.startsWith("46002") || imsi.startsWith("46007")) {
                return OPERATE_TYPE_CMCC;
            } else if (imsi.startsWith("46001")) {
                return OPERATE_TYPE_UNICOM;
            } else if (imsi.startsWith("46003")) {
                return OPERATE_TYPE_TELECOM;
            }
        }
        return OPERATE_TYPE_ONKNOWN;
    }

    public String getImei(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        if (tm == null) {
            return "";
        }
        String imei = tm.getDeviceId();
        if (imei == null) {
            imei = "";
        }
        return imei;
    }

    public boolean isInOverSeaLang() {
        String curLanguage = "";
        curLanguage = Locale.getDefault().getLanguage();
        String country = Locale.getDefault().getCountry();

        if (!(curLanguage.equals("zh") && country.equals("CN"))) {
            return true;
        }

        return false;
    }

    public String getCurLanguage() {
        String curLanguage = "";
        curLanguage = Locale.getDefault().getLanguage();
        if (curLanguage == null) {
            curLanguage = "";
        }
        return curLanguage;
    }

    public String getCurCountry() {
        String curCountry = "";
        curCountry = Locale.getDefault().getCountry();
        if (curCountry == null) {
            curCountry = "";
        }
        return curCountry;
    }

    public String getCurCountry(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getNetworkCountryIso();
    }
}
