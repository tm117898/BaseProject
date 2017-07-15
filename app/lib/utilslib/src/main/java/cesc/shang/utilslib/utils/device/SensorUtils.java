package cesc.shang.utilslib.utils.device;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.List;

/**
 * Created by shanghaolongteng on 2016/7/14.
 */
public class SensorUtils {
    public SensorUtils() {
    }

    public void regiterListener(Context context, SensorEventListener listener) {
        SensorManager manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = manager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (sensors != null && sensors.size() > 0) {
            Sensor sensor = sensors.get(0);
            manager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void unregiterListener(Context context, SensorEventListener listener) {
        SensorManager manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        manager.unregisterListener(listener);
    }
}
