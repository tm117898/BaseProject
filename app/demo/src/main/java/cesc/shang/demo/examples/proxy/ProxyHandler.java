package cesc.shang.demo.examples.proxy;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.base.proxy.InvocationBaseHandler;

/**
 * Created by shanghaolongteng on 2017/8/5.
 */

public class ProxyHandler extends InvocationBaseHandler {
    public ProxyHandler(BaseApplication app) {
        super(app);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("compareTo".equals(method.getName())) {
            ProxyHandler target = (ProxyHandler) Proxy.getInvocationHandler(proxy);
            ProxyHandler arg = (ProxyHandler) Proxy.getInvocationHandler(args[0]);
            int targetId = ((ProxyEntity) target.getTarget()).getId();
            int argId = ((ProxyEntity) arg.getTarget()).getId();
            return targetId - argId;
        }
        return super.invoke(proxy, method, args);
    }

    @Override
    protected void onInvokeStart(Object proxy, Method method, Object[] args) {
        mLog.i("onInvokeStart");
    }

    @Override
    protected void onInvokeEnd(Object proxy, Method method, Object[] args, Object result) {
        mLog.i("onInvokeEnd");
    }
}
