package cesc.shang.demo.examples.okhttp;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.support.BaseManager;
import cesc.shang.baselib.support.callback.IHttpCallBack;
import cesc.shang.baselib.support.manager.HttpManager;
import cesc.shang.utilslib.utils.debug.LogUtils;
import cesc.shang.utilslib.utils.file.XmlUtils;

/**
 * Created by Cesc Shang on 2017/7/17.
 */

public class OkHttpController extends BaseManager {
    public static final String GET_URL = "http://ws.webxml.com.cn/WebServices/WeatherWS.asmx/getWeather?theCityCode=937&theUserID=";
    public static final String POST_URL = "http://gc.ditu.aliyun.com/geocoding?a=%E6%B5%8E%E5%8D%97";

    private LogUtils mLog;

    public OkHttpController(BaseApplication app) {
        super(app);
        mLog = mApp.getUtilsManager().getLogUtils(this.getClass().getSimpleName());
    }

    public void get() {
        HttpManager http = mApp.getAppManager().getHttpManager();
        http.get(mApp, GET_URL, String.class, new IHttpCallBack<String>() {
            @Override
            public void onNetworkDisconnected() {
                mLog.i("get , onNetworkDisconnected()");
            }

            @Override
            public void onFailure() {
                mLog.i("get , onFailure()");
            }

            @Override
            public void onSuccess(String s) {
                try {
                    mApp.getUtilsManager().getXmlUtils().parse(new ByteArrayInputStream(s.getBytes()), new XmlUtils.CallBack() {
                        @Override
                        public void startDocument() {
                            mLog.i("convertEntity , startDocument : ");
                        }

                        @Override
                        public void endDocument() {
                            mLog.i("convertEntity , endDocument : ");
                        }

                        @Override
                        public void startTag(XmlPullParser parser) throws IOException, XmlPullParserException {
                            String text = "string".equals(parser.getName()) ? parser.nextText() : "";
                            mLog.i("convertEntity , startTag : ", parser.getName(), " , text : ", text);
                        }

                        @Override
                        public void endTag(XmlPullParser parser) {
                            mLog.i("convertEntity , endTag : ", parser.getName());
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void post() {
        HttpManager http = mApp.getAppManager().getHttpManager();
        http.post(mApp, POST_URL, null, OkHttpEntity.class, new IHttpCallBack<OkHttpEntity>() {
            @Override
            public void onFailure() {
                mLog.i("post , onFailure()");
            }

            @Override
            public void onNetworkDisconnected() {
                mLog.i("post , onNetworkDisconnected()");
            }

            @Override
            public void onSuccess(OkHttpEntity okHttpEntity) {
                mLog.i("post , onSuccess() , okHttpEntity : ", okHttpEntity);
            }
        });
    }
}
