package cesc.shang.baselib.support.handler;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.support.callback.IHttpCallBack;
import cesc.shang.baselib.support.manager.base.BaseHandler;
import cesc.shang.utilslib.utils.debug.LogUtils;
import cesc.shang.utilslib.utils.device.NetWorkUtils;
import cesc.shang.utilslib.utils.okhttp.OkHttpUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Cesc Shang on 2017/7/17.
 */

public class HttpHandler extends BaseHandler {
    public static final int RESPONSE_SUCCESS_CODE = 200;
    protected LogUtils mLog;
    protected Gson mGson;

    public HttpHandler(BaseApplication app) {
        super(app);
        mLog = app.getUtilsManager().getLogUtils(this.getClass().getSimpleName());
        mGson = new Gson();
    }

    @Override
    public void onDestroy() {
        mGson = null;
    }

    /**
     * 发起post请求
     *
     * @param context     上下文
     * @param url         请求地址
     * @param json        请求参数
     * @param resultClass 返回结果类的Class
     * @param callback    回调
     * @param <T>         结果类泛型
     */
    public <T> void post(
            Context context, String url, JSONObject json, final Class resultClass, final IHttpCallBack<T> callback) {
        NetWorkUtils utils = getUtilsManager().getNetWorkUtils();
        if (utils.isConnected(context)) {
            post(url, json, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    notifyFailure(callback);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    notifyResult(callback, resultClass, response);
                }
            });
        } else {
            notifyNetworkDisconnected(callback);
        }
    }

    /**
     * 发起get请求
     *
     * @param context     上下文
     * @param url         请求地址
     * @param resultClass 返回结果类的Class
     * @param callback    回调
     * @param <T>         结果类泛型
     */
    public <T> void get(Context context, String url, final Class resultClass, final IHttpCallBack<T> callback) {
        NetWorkUtils utils = getUtilsManager().getNetWorkUtils();
        if (utils.isConnected(context)) {
            get(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    notifyFailure(callback);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    notifyResult(callback, resultClass, response);
                }
            });
        } else {
            notifyNetworkDisconnected(callback);
        }
    }

    /**
     * 调用OkHttp发出post请求
     *
     * @param url      请求地址
     * @param json     请求参数
     * @param callback OkHttp回调
     */
    private void post(String url, JSONObject json, Callback callback) {
        OkHttpUtils utils = getUtilsManager().getOkHttpUtils();
        OkHttpClient client = utils.getClient();
        Request request = utils.getPostJsonRequest(url, utils.getRequestBody(json));
        request(callback, utils, client, request);
    }

    private void request(Callback callback, OkHttpUtils utils, OkHttpClient client, Request request) {
        Call call = utils.getCall(client, request);
        utils.enqueue(call, callback);
    }

    /**
     * 调用OkHttp发出get请求
     *
     * @param url      请求地址
     * @param callback OkHttp回调
     */
    private void get(String url, Callback callback) {
        OkHttpUtils utils = getUtilsManager().getOkHttpUtils();
        OkHttpClient client = utils.getClient();
        Request request = utils.getGetJsonRequest(url);
        request(callback, utils, client, request);
    }

    /**
     * 回调通知结果
     *
     * @param callback    回调
     * @param resultClass 返回结果类的Class
     * @param response    服务端返回数据
     * @param <T>         结果类泛型
     * @throws IOException 获取服务端数据出错
     */
    private <T> void notifyResult(IHttpCallBack callback, Class resultClass, Response response) throws IOException {
        if (callback != null) {
            if (response.code() == RESPONSE_SUCCESS_CODE) {
                String result = response.body().string();
                mLog.i("onResponse , result : " + result);
                if (resultClass == String.class) {
                    callback.onSuccess(result);
                } else {
                    try {
                        T t = (T) mGson.fromJson(result, resultClass);
                        callback.onSuccess(t);
                    } catch (Exception e) {
                        e.printStackTrace();
                        notifyFailure(callback);
                    }
                }
            } else {
                notifyFailure(callback);
            }
        }
    }

    /**
     * 回调网络未连接
     *
     * @param callback 回调
     */
    private void notifyNetworkDisconnected(IHttpCallBack callback) {
        if (callback != null) {
            callback.onNetworkDisconnected();
        }
    }

    /**
     * 回调请求失败
     *
     * @param callback 回调
     */
    private void notifyFailure(IHttpCallBack callback) {
        if (callback != null) {
            callback.onFail();
        }
    }
}
