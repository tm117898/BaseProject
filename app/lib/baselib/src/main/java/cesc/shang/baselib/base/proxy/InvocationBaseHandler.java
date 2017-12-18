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

    /**
     * 获取代理目标对象
     *
     * @return 目标对象
     */
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

    /**
     * 函数调用开始
     *
     * @param proxy  代理对象
     * @param method 代理函数
     * @param args   代理参数
     */
    protected abstract void onInvokeStart(Object proxy, Method method, Object[] args);

    /**
     * 函数调用接结束
     *
     * @param proxy  代理对象
     * @param method 代理函数
     * @param args   代理参数
     */
    protected abstract void onInvokeEnd(Object proxy, Method method, Object[] args, Object result);
}
