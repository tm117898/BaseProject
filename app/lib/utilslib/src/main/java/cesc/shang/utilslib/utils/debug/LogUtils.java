package cesc.shang.utilslib.utils.debug;

import android.util.Log;

/**
 * Created by shanghaolongteng on 2016/7/14.
 */
public final class LogUtils {
    private final String tag = "shlt";
    private String className = null;

    private LogUtils() {
    }

    private LogUtils(String className) {
        this.className = className + " , ";
    }

    /**
     * @param className 当前类名称
     * @return LogUtils实例
     */
    public static LogUtils newInstance(String className) {
        return new LogUtils(className);
    }

    /**
     * @param message 要打印的信息，不要使用""+""+"".....模式
     */
    public void v(Object... message) {
        v(null, message);
    }

    /**
     * @param message 要打印的信息，不要使用""+""+"".....模式
     */
    public void d(Object... message) {
        d(null, message);
    }

    /**
     * @param message 要打印的信息，不要使用""+""+"".....模式
     */
    public void i(Object... message) {
        i(null, message);
    }

    /**
     * @param message 要打印的信息，不要使用""+""+"".....模式
     */
    public void w(Object... message) {
        w(null, message);
    }

    /**
     * @param message 要打印的信息，不要使用""+""+"".....模式
     */
    public void e(Object... message) {
        e(null, message);
    }

    /**
     * @param t       堆栈跟踪
     * @param message 要打印的信息，不要使用""+""+"".....模式
     */
    public void v(Throwable t, Object... message) {
        Log.v(tag, className + toMessage(message), t);
    }

    /**
     * @param t       堆栈跟踪
     * @param message 要打印的信息，不要使用""+""+"".....模式
     */
    public void d(Throwable t, Object... message) {
        Log.d(tag, className + toMessage(message), t);
    }

    /**
     * @param t       堆栈跟踪
     * @param message 要打印的信息，不要使用""+""+"".....模式
     */
    public void i(Throwable t, Object... message) {
        Log.i(tag, className + toMessage(message), t);
    }

    /**
     * @param t       堆栈跟踪
     * @param message 要打印的信息，不要使用""+""+"".....模式
     */
    public void w(Throwable t, Object... message) {
        Log.w(tag, className + toMessage(message), t);
    }

    /**
     * @param t       堆栈跟踪
     * @param message 要打印的信息，不要使用""+""+"".....模式
     */
    public void e(Throwable t, Object... message) {
        Log.e(tag, className + toMessage(message), t);
    }

    /**
     * 将打印信息数组转换为String
     *
     * @param message 要打印的信息
     * @return 最终打印的信息
     */
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

