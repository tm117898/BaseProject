package cesc.shang.baselib.support.callback;

/**
 * Created by shanghaolongteng on 2017/7/16.
 */

public interface ISuccessCallBack<T> {
    /**
     * 成功回调
     *
     * @param t 回调参数
     */
    void onSuccess(T t);
}
