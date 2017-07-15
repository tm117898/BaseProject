package cesc.shang.utilslib.utils.util;

import android.os.Handler;
import android.os.Message;

/**
 * Created by shanghaolongteng on 2016/7/14.
 */
public class MessageUtils {
    public MessageUtils() {
    }

    public Message getMessage() {
        return Message.obtain();
    }

    public Message getMessage(Handler handler) {
        return handler.obtainMessage();
    }
}
