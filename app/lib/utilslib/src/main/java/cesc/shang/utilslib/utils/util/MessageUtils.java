package cesc.shang.utilslib.utils.util;

import android.os.Handler;
import android.os.Message;

/**
 * Created by shanghaolongteng on 2016/7/14.
 */
public class MessageUtils {
    public MessageUtils() {
    }

    /**
     * 从全局池中获取一个Message
     *
     * @return Message
     */
    public Message getMessage() {
        return Message.obtain();
    }

    /**
     * 从全局池中获取一个Message
     *
     * @param handler Message.tag = handler
     * @return Message
     */
    public Message getMessage(Handler handler) {
        return handler.obtainMessage();
    }
}
