
package cesc.shang.utilslib.utils.device;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class NetWorkUtils {
    public NetWorkUtils() {
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        context = null;
        if (cm == null) {
            return false;
        }

        NetworkInfo[] info = cm.getAllNetworkInfo();
        cm = null;

        if (info != null) {
            for (int i = 0; i < info.length; i++) {
                if (info[i] != null) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        info = null;
                        return true;
                    }
                }
            }
        }
        info = null;
        return false;
    }

    public boolean isRoaming(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
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

    public String getMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        if (info == null) {
            return "";
        }
        return info.getMacAddress();
    }
}
