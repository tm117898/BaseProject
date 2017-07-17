package cesc.shang.baselib.support.callback;

/**
 * Created by Cesc Shang on 2017/7/17.
 */

public interface IHttpCallBack<T> extends IGetDataCallBack<T> {
    void onNetworkDisconnected();
}
