
package cesc.shang.utilslib.utils.device;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * Created by shanghaolongteng on 2014.03.06
 */
public class AppUtils {
    public static final String INVALID_RESULT_STRING = "unknow";
    public static final int INVALID_RESULT_INT = -1;

    public AppUtils() {
    }

    /**
     * 获取App版本
     *
     * @param context 上下文
     * @return 版本名称，获取失败返回{@link #INVALID_RESULT_STRING}
     */
    public String getAppVersionName(Context context) {
        String version = INVALID_RESULT_STRING;
        PackageInfo packageInfo = getPackageInfo(context);

        if (packageInfo != null) {
            version = packageInfo.versionName;
        }
        return version;
    }

    /**
     * 获取App版本
     *
     * @param context 上下文
     * @return 版本号，获取失败返回 {@link #INVALID_RESULT_INT}
     */
    public int getAppVersionCode(Context context) {
        int code = INVALID_RESULT_INT;
        PackageInfo packageInfo = getPackageInfo(context);

        if (packageInfo != null) {
            code = packageInfo.versionCode;
        }
        return code;
    }

    /**
     * 获取应用包名
     *
     * @param context 上下文
     * @return 应用包名，获取失败返回{@link #INVALID_RESULT_STRING}
     */
    public String getPackageName(Context context) {
        String packageName = INVALID_RESULT_STRING;
        PackageInfo packageInfo = getPackageInfo(context);

        if (packageInfo != null) {
            packageName = packageInfo.packageName;
        }

        return packageName;
    }

    /**
     * 获取应用信息
     *
     * @param context 上下文
     * @return 应用信息，获取失败返回null
     */
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

    /**
     * 获取应用渠道号
     *
     * @param context 上下文
     * @return 渠道号，获取失败返回{@link #INVALID_RESULT_STRING}
     */
    public String getChannelNo(Context context) {
        String str = getAppVersionName(context);
        if ((str == null) || ("".equals(str))) {
            return INVALID_RESULT_STRING;
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

        return INVALID_RESULT_STRING;
    }

    /**
     * 检查指定包名应用是否安装
     *
     * @param context     上下文
     * @param packageName 指定包名
     * @return true已安装，false未安装
     */
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

    /**
     * 获取手机SDK名称
     *
     * @return SDK名称
     */
    public String getSdkName() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机SDK版本号
     *
     * @return SDK版本号
     */
    public int getSdkCode() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取手机制造商
     *
     * @return 手机制造商
     */
    public String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取手机名称
     *
     * @return 手机名称
     */
    public String getModel() {
        String model = Build.MODEL;
        if (model != null) {
            model = model.replaceAll(" ", "");
        }
        return model;
    }


}
