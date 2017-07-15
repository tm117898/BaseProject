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

    public void requestUpdates(Context context, String providerName, long minTime, float minDistance, LocationListener listener) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        manager.requestLocationUpdates(providerName, minTime, minDistance, listener);
    }

    public void removeUpdates(Context context, LocationListener listener) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        manager.removeUpdates(listener);
    }

    public List<String> getAllProviders(Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return manager.getAllProviders();
    }

    public int getPowerRequirement(Context context, String providerName) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        LocationProvider provider = manager.getProvider(providerName);
        return provider.getPowerRequirement();
    }

    public LocationProvider getProviderByCriteria(Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        LocationProvider provider = null;

        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setAltitudeRequired(true);
        criteria.setBearingAccuracy(Criteria.NO_REQUIREMENT);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);//更希望用户设置
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_LOW);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setSpeedAccuracy(Criteria.ACCURACY_MEDIUM);
        criteria.setSpeedRequired(false);
        criteria.setVerticalAccuracy(Criteria.NO_REQUIREMENT);

        List<String> names = manager.getProviders(criteria, false);//完全匹配

        if (names != null && !names.isEmpty()) {
            for (int i = 0; i < names.size(); i++) {
                provider = manager.getProvider(names.get(i));
            }
            provider = manager.getProvider(names.get(0));
        } else {
            String name = manager.getBestProvider(criteria, false);//不完全，放宽条件：1.电力要求2.精度3.方位角4.速度5.海拔高度
            if (!TextUtils.isEmpty(name)) {
                provider = manager.getProvider(name);
            }
        }

        return provider;
    }

    public Location getLastKnownlocation(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                    }
                }
            }
        }

        return location;
    }
}
