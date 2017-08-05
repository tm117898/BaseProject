package cesc.shang.baselib.base.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.utilslib.utils.debug.LogUtils;

/**
 * Created by shanghaolongteng on 2017/8/5.
 */

public abstract class InvocationBaseHandler implements InvocationHandler {
    protected LogUtils mLog;
    private Object mTarget;

    public InvocationBaseHandler(BaseApplication app) {
        mLog = app.getUtilsManager().getLogUtils(getClass().getSimpleName());
    }

    public Object proxy(Object target) {
        mTarget = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    public Object getTarget() {
        return mTarget;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        onInvokeStart(proxy, method, args);
        Object result = method.invoke(mTarget, args);
        mLog.i("invoke , " + method.getName() + " , result : " + (result == null ? "null" : result));
        onInvokeEnd(proxy, method, args, result);
        return result;
    }

    protected abstract void onInvokeStart(Object proxy, Method method, Object[] args);

    protected abstract void onInvokeEnd(Object proxy, Method method, Object[] args, Object result);
}
