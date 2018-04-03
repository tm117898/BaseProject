package cesc.shang.baselib.support.callback;

import java.lang.ref.WeakReference;

/**
 * Created by Cesc Shang on 2018/04/03.
 */
public abstract class SuccessCallBack<T, Reference> implements ISuccessCallBack<T>, AsyncCheckException.Checkable {
    private WeakReference<Reference> mRef;

    public SuccessCallBack(Reference ref) {
        mRef = new WeakReference<>(ref);
    }

    @Override
    public final void onSuccess(T t) {
        Reference r = getRef().get();
        if (r == null) {
            return;
        }
        r = null;

        success(t, getRef());
    }

    /**
     * 重写该方法替代onSuccess()，在使用mRfe之前应该调用checkWeakReferenceInstance()，检查引用是否被回收
     *
     * @param ref
     * @throws AsyncCheckException
     */
    public abstract void success(T t, WeakReference<Reference> ref);

    public WeakReference<Reference> getRef() {
        return mRef;
    }

    @Override
    public void checkWeakReferenceInstance() throws AsyncCheckException {
        Reference r = getRef().get();
        if (r == null) {
            throw new AsyncCheckException("The BaseHandler weakReference instance is null");
        }
    }
}
