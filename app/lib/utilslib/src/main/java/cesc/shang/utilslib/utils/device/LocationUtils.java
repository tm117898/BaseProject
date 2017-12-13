package cesc.shang.utilslib.utils.device;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import java.util.List;

/**
 * Created by shanghaolongteng on 2016/7/14.
 */
public class LocationUtils {
    public LocationUtils() {
    }

    /**
     * 请求获得位置更新
     *
     * @param c            上下文
     * @param providerName LocationProvider
     * @param minTime      最小回调时间
     * @param minDistance  最小回调距离
     * @param l            监听回调
     */
    public void requestUpdates(Context c, String providerName, long minTime, float minDistance, LocationListener l) {
        if (ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationManager manager = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);
        manager.requestLocationUpdates(providerName, minTime, minDistance, l);
    }

    /**
     * 移除位置更新
     *
     * @param context  上下文
     * @param listener 监听回调
     */
    public void removeUpdates(Context context, LocationListener listener) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        manager.removeUpdates(listener);
    }

    /**
     * 获取全部LocationProvider
     *
     * @param context 上下文
     * @return LocationProvider集合
     */
    public List<String> getAllProviders(Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return manager.getAllProviders();
    }

    /**
     * 获取耗电量级别
     *
     * @param context      上下文
     * @param providerName LocationProvider
     * @return Criteria.POWER_REQUIREMENT_*
     */
    public int getPowerRequirement(Context context, String providerName) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        LocationProvider provider = manager.getProvider(providerName);
        return provider.getPowerRequirement();
    }

    /**
     * 获取符合要求的LocationProvider
     *
     * @param context 上下文
     * @return LocationProvider集合
     */
    public LocationProvider getProviderByCriteria(Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        LocationProvider provider = null;

        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setAltitudeRequired(true);
        criteria.setBearingAccuracy(Criteria.NO_REQUIREMENT);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true); //更希望用户设置
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_LOW);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setSpeedAccuracy(Criteria.ACCURACY_MEDIUM);
        criteria.setSpeedRequired(false);
        criteria.setVerticalAccuracy(Criteria.NO_REQUIREMENT);

        List<String> names = manager.getProviders(criteria, false); //完全匹配

        if (names != null && !names.isEmpty()) {
            for (int i = 0; i < names.size(); i++) {
                provider = manager.getProvider(names.get(i));
            }
        } else {
            String name = manager.getBestProvider(criteria, false); //不完全，放宽条件：1.电力要求2.精度3.方位角4.速度5.海拔高度
            if (!TextUtils.isEmpty(name)) {
                provider = manager.getProvider(name);
            }
        }

        return provider;
    }

    /**
     * 获取最后一次已知位置
     *
     * @param context 上下文
     * @return Location，获取失败返回null
     */
    public Location getLastKnownlocation(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }

        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location location = null;
        List<String> names = manager.getAllProviders();

        if (names != null && names.size() > 0) {
            for (int i = 0; i < names.size(); i++) {
                String name = names.get(i);
                if (!LocationManager.PASSIVE_PROVIDER.equals(name)) {
                    Location l = manager.getLastKnownLocation(name);
                    if (l != null && (location == null || l.getTime() > location.getTime())) {
                        location = l;
                        break;
                    }
                }
            }
        }
        return location;
    }
}
