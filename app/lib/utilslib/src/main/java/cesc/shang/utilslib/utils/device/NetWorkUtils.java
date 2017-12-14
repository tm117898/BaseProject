
package cesc.shang.utilslib.utils.device;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by shanghaolongteng on 2016/7/14.
 */
public class NetWorkUtils {
    public static final String INVALID_RESULT_STRING = "unknow";

    public NetWorkUtils() {
    }

    /**
     * 当前是否连接网络
     *
     * @param context 上下文
     * @return true连接，false未连接
     */
    public boolean isConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = getConnectivityManager(context);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 获取ConnectivityManager
     *
     * @param context 上下文
     * @return ConnectivityManager
     */
    private ConnectivityManager getConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    /**
     * 当前网络是否可用
     *
     * @param context 上下文
     * @return true可用，false不可用
     */
    public boolean isRoaming(Context context) {
        ConnectivityManager cm = getConnectivityManager(context);
        NetworkInfo[] info = cm.getAllNetworkInfo();
        if (info != null) {
            for (int i = 0; i < info.length; i++) {
                if (info[i] != null) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取mac地址
     *
     * @param context 上下文
     * @return 返回地址，获取失败返回{@link #INVALID_RESULT_STRING}
     */
    public String getMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        if (info == null) {
            return INVALID_RESULT_STRING;
        }
        return info.getMacAddress();
    }
}
