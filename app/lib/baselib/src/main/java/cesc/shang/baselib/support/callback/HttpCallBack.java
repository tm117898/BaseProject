package cesc.shang.baselib.support.callback;

import java.lang.ref.WeakReference;

/**
 * Created by Cesc Shang on 2018/04/03.
 */
public abstract class HttpCallBack<T, Reference> extends SuccessCallBack<T, Reference> implements IHttpCallBack<T> {
    public HttpCallBack(Reference ref) {
        super(ref);
    }

    @Override
    public void onNetworkDisconnected() {
        Reference r = getRef().get();
        if (r == null) {
            return;
        }
        r = null;

        networkDisconnected(getRef());
    }

    /**
     * 重写该方法替代onNetworkDisconnected()，在使用mRfe之前应该调用checkWeakReferenceInstance()，检查引用是否被回收
     *
     * @param ref
     * @throws AsyncCheckException
     */
    public abstract void networkDisconnected(WeakReference<Reference> ref);
}
