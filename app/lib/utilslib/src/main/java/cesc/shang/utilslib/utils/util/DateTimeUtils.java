
package cesc.shang.utilslib.utils.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.DateFormat;

@SuppressLint("SimpleDateFormat")
public class DateTimeUtils {
    public static final String TODAY_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String TODAY_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public DateTimeUtils() {

    }

    public String getTodayString() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat(TODAY_FORMAT);
        return format.format(now);
    }

    public String getTimeString() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT);
        return format.format(now);
    }

    public String getTodayTimeString() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat(TODAY_TIME_FORMAT);
        return format.format(now);
    }

    public long getBetweenDateOfMilliseconds(String date1, String date2) {
        long result = Long.MAX_VALUE;
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
        dateFormat = null;
        d1 = null;
        d2 = null;
        date1 = null;
        date2 = null;

        return result;
    }

    public long getBetweenCurToDateOfMilliseconds(String date) {
        long result = Long.MAX_VALUE;
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
        dateFormat = null;
        d1 = null;
        d2 = null;
        date = null;

        return result;
    }

    public long getBetweenDateOfMilliseconds(Date date1, Date date2) {
        long result = date1.getTime() - date2.getTime();
        result = Math.abs(result);
        date1 = null;
        date2 = null;
        return result;
    }

    /**
     * 获取时间制式
     *
     * @param context
     *
     * @return
     */
    public boolean getSystemTimeFormat(Context context) {
        boolean is24HourFormat = DateFormat.is24HourFormat(context);
        context = null;
        return is24HourFormat;
    }
}
