package cesc.shang.baselib.base.async;

import android.util.Log;

import java.lang.ref.WeakReference;

import cesc.shang.baselib.support.context.IContextSupport;

/**
 * Created by Cesc Shang on 2018/03/22.
 */

public abstract class BaseRunnable<T extends IContextSupport> implements Runnable, AsyncCheckException.Checkable {
    private WeakReference<T> mRef;

    public BaseRunnable(T t) {
        this.mRef = new WeakReference<>(t);
    }

    private WeakReference<T> getRef() {
        return mRef;
    }

    @Override
    public final void run() {
        T t = getRef().get();
        if (t == null) {
            return;
        }
        t = null;

        try {
            running(getRef());
        } catch (AsyncCheckException e) {
            Log.i("BaseRunnable ", " AsyncCheckException");
        }
    }

    /**
     * 重写该方法替代run()，在使用mRfe之前应该调用checkWeakReferenceInstance()，检查引用是否被回收
     *
     * @param ref
     * @throws AsyncCheckException
     */
    public abstract void running(WeakReference<T> ref) throws AsyncCheckException;

    public void checkWeakReferenceInstance() throws AsyncCheckException {
        T t = getRef().get();
        if (t == null) {
            throw new AsyncCheckException("The BaseRunnable weakReference instance is null");
        }
    }
}
