package cesc.shang.utilslib.utils.okhttp;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Cesc Shang on 2017/7/17.
 */

public class OkHttpUtils {
    public OkHttpClient getClient() {
        return new OkHttpClient();
    }

    public Request getPostJsonRequest(String url, RequestBody body) {
        Request request = getBaseRequest(url).post(body).build();
        return request;
    }

    private Request.Builder getBaseRequest(String url) {
        return new Request.Builder().url(url);
    }

    public Request getGetJsonRequest(String url) {
        Request request = getBaseRequest(url).get().build();
        return request;
    }

    public RequestBody getRequestBody(JSONObject json) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json == null ? "" : json.toString());
    }

    public Call getCall(OkHttpClient client, Request request) {
        return client.newCall(request);
    }

    public void enqueue(Call call, Callback callback) {
        call.enqueue(callback);
    }
}
