package cesc.shang.baselib.support.manager;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.support.BaseManager;
import cesc.shang.baselib.support.callback.IHttpCallBack;
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

public class HttpManager extends BaseManager {
    protected LogUtils mLog;
    protected Gson mGson;

    public HttpManager(BaseApplication app) {
        super(app);
        mLog = app.getUtilsManager().getLogUtils(this.getClass().getSimpleName());
        mGson = new Gson();
    }

    public <T> void post(Context context, String url, JSONObject json, final Class resultClass, final IHttpCallBack callback) {
        NetWorkUtils utils = mApp.getUtilsManager().getNetWorkUtils();
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

    public <T> void get(Context context, String url, final Class resultClass, final IHttpCallBack callback) {
        NetWorkUtils utils = mApp.getUtilsManager().getNetWorkUtils();
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

    private void post(String url, JSONObject json, Callback callback) {
        OkHttpUtils utils = mApp.getUtilsManager().getOkHttpUtils();
        OkHttpClient client = utils.getClient();
        Request request = utils.getPostJsonRequest(url, utils.getRequestBody(json));
        request(callback, utils, client, request);
    }

    private void request(Callback callback, OkHttpUtils utils, OkHttpClient client, Request request) {
        Call call = utils.getCall(client, request);
        utils.enqueue(call, callback);
    }

    private void get(String url, Callback callback) {
        OkHttpUtils utils = mApp.getUtilsManager().getOkHttpUtils();
        OkHttpClient client = utils.getClient();
        Request request = utils.getGetJsonRequest(url);
        request(callback, utils, client, request);
    }

    private <T> void notifyResult(IHttpCallBack callback, Class resultClass, Response response) throws IOException {
        if (callback != null) {
            if (response.code() == 200) {
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
                    }

                }
            } else {
                callback.onFailure();
            }
        }
    }

    private void notifyNetworkDisconnected(IHttpCallBack callback) {
        if (callback != null) {
            callback.onNetworkDisconnected();
        }
    }

    private void notifyFailure(IHttpCallBack callback) {
        if (callback != null) {
            callback.onFailure();
        }
    }

}
