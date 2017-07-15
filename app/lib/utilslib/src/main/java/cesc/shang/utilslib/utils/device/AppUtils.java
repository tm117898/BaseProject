
package cesc.shang.utilslib.utils.device;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.util.Locale;

/**
 * Created by shanghaolongteng on 2014.03.06
 */
public class AppUtils {
    public AppUtils() {

    }

    /**
     * get version form Androidmanifest.xml
     *
     * @return
     */
    public String getAppVersionName(Context context) {
        String version = "unknow";
        PackageInfo packageInfo = getPackageInfo(context);

        if (packageInfo != null) {
            version = packageInfo.versionName;
        }
        return version;
    }

    public int getAppVersionCode(Context context) {
        int code = -1;
        PackageInfo packageInfo = getPackageInfo(context);

        if (packageInfo != null) {
            code = packageInfo.versionCode;
        }
        return code;
    }

    public String getPackageName(Context context) {
        String packageName = null;
        PackageInfo packageInfo = getPackageInfo(context);

        if (packageInfo != null) {
            packageName = packageInfo.packageName;
        }

        return packageName;
    }

    public PackageInfo getPackageInfo(Context context) {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager != null) {
            try {
                return packageManager.getPackageInfo(context.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getChannelNo(Context context) {
        String str = getAppVersionName(context);
        if ((str == null) || ("".equals(str))) {
            return "";
        } else {
            int index = str.lastIndexOf(".");
            int len = str.length();
            if ((index >= 0) && (index < len)) {
                String tempStr = str.substring(index + 1, len);
                if ((null != tempStr) && (!"".equals(tempStr))) {
                    return tempStr;
                }
            }
        }

        return "";
    }

    public boolean checkApkInstallByAppPackage(Context context, String packageName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (Exception e) {
            packageInfo = null;
            e.printStackTrace();
        }

        context = null;

        if (packageInfo == null) {
            return false;
        } else {
            packageInfo = null;
            return true;
        }
    }


    public String getSdkName() {
        return Build.VERSION.RELEASE;
    }

    public int getSdkCode() {
        return Build.VERSION.SDK_INT;
    }

    public String getManufacturer() {
        return Build.MANUFACTURER;
    }

    public String getModel() {
        String model = Build.MODEL;
        if (model != null) {
            model = model.replaceAll(" ", "");
        }
        return model;
    }


}
