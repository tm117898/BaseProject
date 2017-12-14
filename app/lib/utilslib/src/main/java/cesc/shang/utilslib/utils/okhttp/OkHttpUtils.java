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
    /**
     * @return OkHttpClient
     */
    public OkHttpClient getClient() {
        return new OkHttpClient();
    }

    /**
     * Post请求的Request
     *
     * @param url  地址
     * @param body @{@link #getRequestBody(JSONObject)}
     * @return Request
     */
    public Request getPostJsonRequest(String url, RequestBody body) {
        Request request = getBaseRequest(url).post(body).build();
        return request;
    }

    /**
     * @param url 地址
     * @return Request.Builder
     */
    private Request.Builder getBaseRequest(String url) {
        return new Request.Builder().url(url);
    }

    /**
     * Get请求的Request
     *
     * @param url 地址
     * @return Request
     */
    public Request getGetJsonRequest(String url) {
        Request request = getBaseRequest(url).get().build();
        return request;
    }

    /**
     * @param json 请求的参数
     * @return RequestBody
     */
    public RequestBody getRequestBody(JSONObject json) {
        return RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"), json == null ? "" : json.toString()
        );
    }

    /**
     * 获取一个请求
     *
     * @param client  @{@link #getClient()}
     * @param request @{@link #getPostJsonRequest(String, RequestBody)}  or @{@link #getGetJsonRequest(String)}
     * @return Call
     */
    public Call getCall(OkHttpClient client, Request request) {
        return client.newCall(request);
    }

    /**
     * 将请求放入请求队列中
     *
     * @param call     @{@link #getCall(OkHttpClient, Request)}
     * @param callback 回调
     */
    public void enqueue(Call call, Callback callback) {
        call.enqueue(callback);
    }
}
