package cesc.shang.baselib.base.socket;

/**
 * Created by shanghaolongteng on 2016/8/2.
 */
public interface SocketClientListener {
    /**
     * 收到客户端注册消息
     *
     * @param key    客户端对应key
     * @param client 客户端的client
     */
    void register(Object key, BaseSocketClient client);

    /**
     * 有客户端下线
     *
     * @param client 客户端的client
     */
    void breakLine(BaseSocketClient client);
}
