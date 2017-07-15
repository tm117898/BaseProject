package cesc.shang.baselib.base.socket;

/**
 * Created by shanghaolongteng on 2016/8/2.
 */
public interface SocketClientListener {
    void register(Object key, BaseSocketClient client);

    void breakLine(BaseSocketClient client);
}
