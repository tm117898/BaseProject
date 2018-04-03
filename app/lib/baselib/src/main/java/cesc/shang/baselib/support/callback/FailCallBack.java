package cesc.shang.baselib.support.callback;

import java.lang.ref.WeakReference;

/**
 * Created by Cesc Shang on 2018/04/03.
 */
public abstract class FailCallBack<T, Reference> extends SuccessCallBack<T, Reference> implements IFailCallBack<T> {
    public FailCallBack(Reference ref) {
        super(ref);
    }

    @Override
    public void onFail() {
        Reference r = getRef().get();
        if (r == null) {
            return;
        }
        r = null;

        fail(getRef());
    }

    /**
     * 重写该方法替代onFail()，在使用mRfe之前应该调用checkWeakReferenceInstance()，检查引用是否被回收
     *
     * @param ref
     * @throws AsyncCheckException
     */
    public abstract void fail(WeakReference<Reference> ref);
}
