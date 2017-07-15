
package cesc.shang.utilslib.utils.device;

import android.app.PendingIntent;
import android.content.Context;

import java.lang.reflect.Method;

/**
 * Created by shanghaolongteng on 2014.03.06
 */
public class DualSimUtils {
    public DualSimUtils() {
    }

    /**
     * IMSI
     */
    @SuppressWarnings("unchecked")
    public String getImsi(int card) {
        String imsi = null;

        try {
            @SuppressWarnings("rawtypes")
            Class clazz = Class.forName("android.telephony.MSimTelephonyManager");
            Method method = clazz.getMethod("getDefault", new Class[]{});
            Object object = method.invoke(null, new Object[]{});
            method = clazz.getMethod("getSubscriberId", new Class[]{
                    int.class
            });
            imsi = (String) method.invoke(object, new Object[]{
                    card
            });
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }

        if (imsi == null) {
            try {
                @SuppressWarnings("rawtypes")
                Class clazz = Class.forName("com.mediatek.telephony.TelephonyManagerEx");
                Method method = clazz.getMethod("getDefault", new Class[]{});
                Object object = method.invoke(null, new Object[]{});
                method = clazz.getMethod("getSubscriberId", new Class[]{
                        int.class
                });
                imsi = (String) method.invoke(object, new Object[]{
                        card
                });

            } catch (Exception e) {
                e.printStackTrace();
            } catch (Error e) {
                e.printStackTrace();
            }
        }

        if (null == imsi) {
            try {
                @SuppressWarnings("rawtypes")
                Class clazz = Class.forName("android.telephony.TelephonyManager");
                Method method = clazz.getMethod("getDefault", new Class[]{
                        int.class
                });
                Object object = method.invoke(null, new Object[]{
                        card
                });
                method = clazz.getMethod("getSubscriberId", new Class[]{});
                imsi = (String) method.invoke(object, new Object[]{});
            } catch (Exception e) {
                e.printStackTrace();
            } catch (Error e) {
                e.printStackTrace();
            }
        }

        if (null == imsi) {
            imsi = "";
        }
        return imsi;
    }

    /**
     * ICCID
     */
    @SuppressWarnings("unchecked")
    public String getIccid(int card) {
        String iccid = null;

        try {
            @SuppressWarnings("rawtypes")
            Class clazz = Class.forName("android.telephony.MSimTelephonyManager");
            Method method = clazz.getMethod("getDefault", new Class[]{});
            Object object = method.invoke(null, new Object[]{});
            method = clazz.getMethod("getSimSerialNumber", new Class[]{
                    int.class
            });
            iccid = (String) method.invoke(object, new Object[]{
                    card
            });

        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }

        if (null == iccid) {
            try {
                @SuppressWarnings("rawtypes")
                Class clazz = Class.forName("com.mediatek.telephony.TelephonyManagerEx");
                Method method = clazz.getMethod("getDefault", new Class[]{});
                Object object = method.invoke(null, new Object[]{});
                method = clazz.getMethod("getSimSerialNumber", new Class[]{
                        int.class
                });
                iccid = (String) method.invoke(object, new Object[]{
                        card
                });

            } catch (Exception e) {
                e.printStackTrace();
            } catch (Error e) {
                e.printStackTrace();
            }
        }

        if (null == iccid) {

            try {
                @SuppressWarnings("rawtypes")
                Class clazz = Class.forName("android.telephony.TelephonyManager");
                Method method = clazz.getMethod("getDefault", new Class[]{
                        int.class
                });
                Object object = method.invoke(null, new Object[]{
                        card
                });
                method = clazz.getMethod("getSimSerialNumber", new Class[]{});
                iccid = (String) method.invoke(object, new Object[]{});
            } catch (Exception e) {
                e.printStackTrace();
            } catch (Error e) {
                e.printStackTrace();
            }
        }

        if (null == iccid) {
            iccid = "";
        }
        return iccid;
    }

    /**
     * sim
     */
    @SuppressWarnings({
            "unchecked", "rawtypes"
    })
    public int getDefaultSim(Context context) {
        // ���ø�ͨƽ̨�Ľӿڻ�ȡ
        try {
            Class clazz = Class.forName("android.telephony.MSimTelephonyManager");
            Method method = clazz.getMethod("getDefault", new Class[]{});
            Object object = method.invoke(null, new Object[]{});
            method = clazz.getMethod("getDefaultSubscription", new Class[]{});
            Integer index = (Integer) method.invoke(object, new Object[]{});
            return index;
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }

        // ��ȡSPRDƽ̨�Ľӿڻ�ȡ
        try {
            Class clazz = Class.forName("android.telephony.TelephonyManager");
            Method method = clazz.getMethod("getDefault", new Class[]{});
            Object object = method.invoke(null, new Object[]{});
            Class[] args = new Class[]{
                    Context.class
            };
            method = clazz.getMethod("getDefaultSim", args);
            Integer index = (Integer) method.invoke(object, null, 1);
            return index;
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }

        // ��ȡMTKƽ̨�Ľӿڻ�ȡ
        try {
            Class clazz = Class.forName("android.telephony.TelephonyManager");
            Method method = clazz.getMethod("getDefault", new Class[]{});
            Object object = method.invoke(null, new Object[]{});
            method = clazz.getMethod("getSmsDefaultSim", new Class[]{});
            return (Integer) method.invoke(object, new Object[]{});
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * �����ı�����Ϣ
     */
    @SuppressWarnings({
            "rawtypes", "unchecked"
    })
    public void sendTextMessage(String destinationAddress, String scAddress, String text, PendingIntent sentIntent, PendingIntent deliveryIntent, int card) {
        // ���ø�ͨƽ̨�Ľӿڻ�ȡ
        try {
            Class clazz = Class.forName("android.telephony.MSimSmsManager");
            Class[] args = new Class[]{
                    String.class, String.class, String.class, PendingIntent.class, PendingIntent.class, int.class
            };

            Method method = clazz.getMethod("sendTextMessage", args);
            method.invoke(clazz.newInstance(), destinationAddress, scAddress, text, sentIntent, deliveryIntent, card);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }

        // ����SPRDƽ̨�Ľӿڻ�ȡ
        try {
            Class clazz = Class.forName("android.telephony.SmsManager");

            Class[] args1 = new Class[]{
                    int.class
            };
            Method method = clazz.getDeclaredMethod("getDefault", args1);
            Object object = method.invoke(null, new Object[]{
                    card
            });
            Class[] args2 = new Class[]{
                    String.class, String.class, String.class, PendingIntent.class, PendingIntent.class
            };

            method = clazz.getMethod("sendTextMessage", args2);
            method.invoke(object, destinationAddress, scAddress, text, sentIntent, deliveryIntent);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }

        // ����MTKƽ̨�Ľӿڻ�ȡ
        try {
            Class clazz = Class.forName("com.mediatek.telephony.SmsManager");
            Class[] args = new Class[]{
                    String.class, String.class, String.class, int.class, PendingIntent.class, PendingIntent.class
            };

            Method method = clazz.getMethod("sendTextMessage", args);
            method.invoke(clazz.newInstance(), destinationAddress, scAddress, text, card, sentIntent, deliveryIntent);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }
    }
}
