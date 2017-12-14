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

    /**
     * 注册加速度传感器监听
     *
     * @param context  上下文
     * @param listener 回调
     */
    public void regiterListener(Context context, SensorEventListener listener) {
        SensorManager manager = getSensorManager(context);
        List<Sensor> sensors = manager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (sensors != null && sensors.size() > 0) {
            Sensor sensor = sensors.get(0);
            manager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    /**
     * 获取SensorManager
     *
     * @param context 上下文
     * @return SensorManager
     */
    private SensorManager getSensorManager(Context context) {
        return (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    /**
     * 反注册加速度传感器监听
     *
     * @param context  上下文
     * @param listener 回调
     */
    public void unregiterListener(Context context, SensorEventListener listener) {
        SensorManager manager = getSensorManager(context);
        manager.unregisterListener(listener);
    }
}
