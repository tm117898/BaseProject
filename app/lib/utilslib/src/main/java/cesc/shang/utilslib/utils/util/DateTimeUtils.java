
package cesc.shang.utilslib.utils.util;

import android.content.Context;
import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shanghaolongteng on 2016/8/2.
 */
public class DateTimeUtils {
    public static final String TODAY_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String TODAY_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final int INVALID_RESULT_INT = -1;

    public DateTimeUtils() {

    }

    /**
     * 获取当前日期
     *
     * @return 年-月-日
     */
    public String getTodayString() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat(TODAY_FORMAT);
        return format.format(now);
    }

    /**
     * 获取当前时间
     *
     * @return 时：分：秒
     */
    public String getTimeString() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT);
        return format.format(now);
    }

    /**
     * 获取当前时期+时间
     *
     * @return 年-月-日 时：分：秒
     */
    public String getTodayTimeString() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat(TODAY_TIME_FORMAT);
        return format.format(now);
    }

    /**
     * 获取两个时间之间的差值
     *
     * @param date1 年-月-日 时：分：秒
     * @param date2 年-月-日 时：分：秒
     * @return 毫秒，获取失败返回{@link #INVALID_RESULT_INT}
     */
    public long getBetweenDateOfMilliseconds(String date1, String date2) {
        long result = INVALID_RESULT_INT;
        Date d1 = null;
        Date d2 = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(TODAY_TIME_FORMAT);
        try {
            d1 = dateFormat.parse(date1);
            d2 = dateFormat.parse(date2);
            result = getBetweenDateOfMilliseconds(d1, d2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 获取指定日期与当前日期之间的差值
     *
     * @param date 年-月-日 时：分：秒
     * @return 毫秒，获取失败返回{@link #INVALID_RESULT_INT}
     */
    public long getBetweenCurToDateOfMilliseconds(String date) {
        long result = INVALID_RESULT_INT;
        Date d1 = null;
        Date d2 = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(TODAY_TIME_FORMAT);
        try {
            d1 = dateFormat.parse(date);
            d2 = dateFormat.parse(dateFormat.format(new Date()));
            result = getBetweenDateOfMilliseconds(d1, d2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 获取两个时间之间的差值
     *
     * @param date1 年-月-日 时：分：秒
     * @param date2 年-月-日 时：分：秒
     * @return 毫秒
     */
    public long getBetweenDateOfMilliseconds(Date date1, Date date2) {
        long result = date1.getTime() - date2.getTime();
        result = Math.abs(result);
        return result;
    }

    /**
     * 获取时间制式是都是24小时制
     *
     * @param context 上下文
     * @return true是，false不是
     */
    public boolean getSystemTimeFormat(Context context) {
        boolean is24HourFormat = DateFormat.is24HourFormat(context);
        return is24HourFormat;
    }
}
