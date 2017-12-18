package cesc.shang.baselib.support.callback;

/**
 * Created by shanghaolongteng on 2017/7/16.
 */

public interface IFailCallBack<T> extends ISuccessCallBack<T> {
    /**
     * 失败回调
     */
    void onFail();
}
