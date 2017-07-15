package cesc.shang.utilslib.utils.debug;

import android.util.Log;

import java.util.Objects;

/**
 * Created by shanghaolongteng on 2016/7/14.
 */
public class LogUtils {
    private final String LOG_TAG = "shlt";
    private String className = null;

    private LogUtils() {
    }

    private LogUtils(String className) {
        this.className = className + " , ";
        className = null;
    }

    public static LogUtils newInstance(String className) {
        return new LogUtils(className);
    }

    public void v(Object... message) {
        Log.v(LOG_TAG, className + toMessage(message));
    }

    public void d(Object... message) {
        Log.d(LOG_TAG, className + toMessage(message));
    }

    public void i(Object... message) {
        Log.i(LOG_TAG, className + toMessage(message));
    }

    public void w(Object... message) {
        Log.w(LOG_TAG, className + toMessage(message));
    }

    public void e(Object... message) {
        Log.e(LOG_TAG, className + toMessage(message));
    }

    private String toMessage(Object... message) {
        if (message == null || message.length == 0) {
            return null;
        } else {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < message.length; i++) {
                Object object = message[i];
                if (object == null) {
                    builder.append("null");
                } else {
                    builder.append(message[i].toString());
                }
            }
            String s = builder.toString();
            builder.setLength(0);
            return s;
        }
    }
}
