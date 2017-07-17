package cesc.shang.utilslib.utils.file;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by shanghaolongteng on 2016/8/30.
 */
public class XmlUtils {
    public XmlUtils() {
    }

    public void parse(InputStream is, CallBack callBack) throws Exception {
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(is, "UTF-8");
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:// 判断当前事件是否为文档开始事件
                    callBack.startDocument();
                    break;
//                case XmlPullParser.END_DOCUMENT:// 判断当前事件是否为文档结束事件
//                    break;
                case XmlPullParser.START_TAG:// 判断当前事件是否为标签元素开始事件
                    callBack.startTag(parser);
                    break;
                case XmlPullParser.END_TAG:// 判断当前事件是否为标签元素结束事件
                    callBack.endTag(parser);
                    break;
            }
            eventType = parser.next();
        }
        callBack.endDocument();
    }

    public interface CallBack {
        void startDocument();

        void endDocument();

        void startTag(XmlPullParser parser) throws IOException, XmlPullParserException;

        void endTag(XmlPullParser parser);
    }
}
