
package cesc.shang.utilslib.utils.device;

import android.app.PendingIntent;
import android.content.Context;
import android.text.TextUtils;

import java.lang.reflect.Method;

/**
 * Created by shanghaolongteng on 2014.03.06
 */
public class DualSimUtils {
    public static final String INVALID_RESULT_STRING = "unknow";
    public static final int INVALID_RESULT_INT = -1;

    public DualSimUtils() {
    }

    /**
     * 根据sim的id获取IMSI
     *
     * @param card sim的id
     * @return IMSI，获取失败返回{@link #INVALID_RESULT_STRING}
     */
    public String getImsi(int card) {
        String imsi = INVALID_RESULT_STRING;

        try {
            Class clazz = Class.forName("android.telephony.MSimTelephonyManager");
            Method method = clazz.getMethod("getDefault", new Class[]{});
            Object object = method.invoke(null, new Object[]{});
            method = clazz.getMethod("getSubscriberId", new Class[]{int.class});
            imsi = (String) method.invoke(object, new Object[]{card});
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!TextUtils.isEmpty(imsi) && !INVALID_RESULT_STRING.equals(imsi)) {
            return imsi;
        }

        try {
            Class clazz = Class.forName("com.mediatek.telephony.TelephonyManagerEx");
            Method method = clazz.getMethod("getDefault", new Class[]{});
            Object object = method.invoke(null, new Object[]{});
            method = clazz.getMethod("getSubscriberId", new Class[]{int.class});
            imsi = (String) method.invoke(object, new Object[]{card});
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!TextUtils.isEmpty(imsi) && !INVALID_RESULT_STRING.equals(imsi)) {
            return imsi;
        }

        try {
            Class clazz = Class.forName("android.telephony.TelephonyManager");
            Method method = clazz.getMethod("getDefault", new Class[]{int.class});
            Object object = method.invoke(null, new Object[]{card});
            method = clazz.getMethod("getSubscriberId", new Class[]{});
            imsi = (String) method.invoke(object, new Object[]{});
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(imsi)) {
            imsi = INVALID_RESULT_STRING;
        }
        return imsi;
    }

    /**
     * 根据sim的id获取ICCID
     *
     * @param card sim的id
     * @return ICCID，获取失败返回{@link #INVALID_RESULT_STRING}
     */
    public String getIccid(int card) {
        String iccid = INVALID_RESULT_STRING;

        try {
            Class clazz = Class.forName("android.telephony.MSimTelephonyManager");
            Method method = clazz.getMethod("getDefault", new Class[]{});
            Object object = method.invoke(null, new Object[]{});
            method = clazz.getMethod("getSimSerialNumber", new Class[]{int.class});
            iccid = (String) method.invoke(object, new Object[]{card});
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!TextUtils.isEmpty(iccid) && !INVALID_RESULT_STRING.equals(iccid)) {
            return iccid;
        }

        try {
            Class clazz = Class.forName("com.mediatek.telephony.TelephonyManagerEx");
            Method method = clazz.getMethod("getDefault", new Class[]{});
            Object object = method.invoke(null, new Object[]{});
            method = clazz.getMethod("getSimSerialNumber", new Class[]{int.class});
            iccid = (String) method.invoke(object, new Object[]{card});
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!TextUtils.isEmpty(iccid) && !INVALID_RESULT_STRING.equals(iccid)) {
            return iccid;
        }

        try {
            Class clazz = Class.forName("android.telephony.TelephonyManager");
            Method method = clazz.getMethod("getDefault", new Class[]{int.class});
            Object object = method.invoke(null, new Object[]{card});
            method = clazz.getMethod("getSimSerialNumber", new Class[]{});
            iccid = (String) method.invoke(object, new Object[]{});
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(iccid)) {
            iccid = INVALID_RESULT_STRING;
        }
        return iccid;
    }

    /**
     * 获取默认SIM卡id
     *
     * @param context 上下文
     * @return SIM卡id，获取失败返回{@link #INVALID_RESULT_INT}
     */
    public int getDefaultSim(Context context) {
        int id = INVALID_RESULT_INT;

        try {
            Class clazz = Class.forName("android.telephony.MSimTelephonyManager");
            Method method = clazz.getMethod("getDefault", new Class[]{});
            Object object = method.invoke(null, new Object[]{});
            method = clazz.getMethod("getDefaultSubscription", new Class[]{});
            Integer index = (Integer) method.invoke(object, new Object[]{});
            id = index == null ? INVALID_RESULT_INT : index;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (id > INVALID_RESULT_INT) {
            return id;
        }

        try {
            Class clazz = Class.forName("android.telephony.TelephonyManager");
            Method method = clazz.getMethod("getDefault", new Class[]{});
            Object object = method.invoke(null, new Object[]{});
            Class[] args = new Class[]{Context.class};
            method = clazz.getMethod("getDefaultSim", args);
            Integer index = (Integer) method.invoke(object, null, 1);
            id = index == null ? INVALID_RESULT_INT : index;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (id > INVALID_RESULT_INT) {
            return id;
        }

        try {
            Class clazz = Class.forName("android.telephony.TelephonyManager");
            Method method = clazz.getMethod("getDefault", new Class[]{});
            Object object = method.invoke(null, new Object[]{});
            method = clazz.getMethod("getSmsDefaultSim", new Class[]{});
            Integer index = (Integer) method.invoke(object, new Object[]{});
            id = index == null ? INVALID_RESULT_INT : index;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    /**
     * 根据SIM卡id发送信息
     *
     * @param destinationAddress
     * @param scAddress
     * @param text
     * @param sentIntent
     * @param deliveryIntent
     * @param card
     * @return true成功，false失败
     */
    public boolean sendTextMessage(String destinationAddress, String scAddress, String text, PendingIntent sentIntent,
                                   PendingIntent deliveryIntent, int card) {
        boolean result = false;

        try {
            Class clazz = Class.forName("android.telephony.MSimSmsManager");
            Class[] args = new Class[]{String.class, String.class, String.class, PendingIntent.class, PendingIntent.class, int.class};
            Method method = clazz.getMethod("sendTextMessage", args);
            method.invoke(clazz.newInstance(), destinationAddress, scAddress, text, sentIntent, deliveryIntent, card);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (result) {
            return true;
        }

        try {
            Class clazz = Class.forName("android.telephony.SmsManager");
            Class[] args1 = new Class[]{int.class};
            Method method = clazz.getDeclaredMethod("getDefault", args1);
            Object object = method.invoke(null, new Object[]{card});
            Class[] args2 = new Class[]{String.class, String.class, String.class, PendingIntent.class, PendingIntent.class};
            method = clazz.getMethod("sendTextMessage", args2);
            method.invoke(object, destinationAddress, scAddress, text, sentIntent, deliveryIntent);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (result) {
            return true;
        }

        try {
            Class clazz = Class.forName("com.mediatek.telephony.SmsManager");
            Class[] args = new Class[]{String.class, String.class, String.class, int.class, PendingIntent.class, PendingIntent.class};
            Method method = clazz.getMethod("sendTextMessage", args);
            method.invoke(clazz.newInstance(), destinationAddress, scAddress, text, card, sentIntent, deliveryIntent);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
