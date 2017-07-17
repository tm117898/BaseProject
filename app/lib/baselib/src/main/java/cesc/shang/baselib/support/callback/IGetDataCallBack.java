package cesc.shang.baselib.support.callback;

/**
 * Created by shanghaolongteng on 2017/7/16.
 */

public interface IGetDataCallBack<T> extends IGetDataSuccessCallBack<T> {
    void onFailure();
}
