package cesc.shang.baselib.base.async;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.lang.ref.WeakReference;

import cesc.shang.baselib.support.callback.AsyncCheckException;
import cesc.shang.baselib.support.context.IContextSupport;

/**
 * Created by Cesc Shang on 2018/03/22.
 */

public abstract class BaseHandler<T extends IContextSupport> extends Handler implements AsyncCheckException.Checkable {
    private WeakReference<T> mRef;

    public BaseHandler(T t) {
        super(Looper.getMainLooper());
        this.mRef = new WeakReference<>(t);
    }

    public BaseHandler(Looper looper, T t) {
        super(looper);
        this.mRef = new WeakReference<>(t);
    }

    public WeakReference<T> getRef() {
        return mRef;
    }

    @Override
    public final void handleMessage(Message msg) {
        super.handleMessage(msg);

        T t = getRef().get();
        if (t == null) {
            return;
        }
        t = null;

        try {
            handlingMessage(msg, getRef());
        } catch (AsyncCheckException e) {
            Log.i("BaseHandler ", " AsyncCheckException");
        }
    }

    /**
     * 重写该方法替代handleMessage()，在使用mRfe之前应该调用checkWeakReferenceInstance()，检查引用是否被回收
     *
     * @param ref
     * @throws AsyncCheckException
     */
    protected abstract void handlingMessage(Message msg, WeakReference<T> ref) throws AsyncCheckException;

    @Override
    public void checkWeakReferenceInstance() throws AsyncCheckException {
        T t = getRef().get();
        if (t == null) {
            throw new AsyncCheckException("The BaseHandler weakReference instance is null");
        }
    }
}
