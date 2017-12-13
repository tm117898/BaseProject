package cesc.shang.utilslib.utils.file;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by shanghaolongteng on 2016/8/30.
 * <p>
 * 使用Pull解析器解析XML
 */
public class XmlUtils {
    public XmlUtils() {
    }

    /**
     * 解析文档
     *
     * @param is       待解析文档对应InputStream
     * @param callBack 回调接口
     * @throws XmlPullParserException Pull解析器抛出异常
     * @throws IOException            Pull解析器抛出异常
     */
    public void parse(InputStream is, CallBack callBack) throws XmlPullParserException, IOException {
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
                default:
                    break;
            }
            eventType = parser.next();
        }
        callBack.endDocument();
    }

    /**
     * 文档解析回调
     */
    public interface CallBack {
        /**
         * 开始解析文档
         */
        void startDocument();

        /**
         * 结束解析文档
         */
        void endDocument();

        /**
         * 进入标签
         *
         * @param parser parser.getName()获取标签名
         *               parser.nextText()获取标签内文字
         * @throws IOException            Pull解析器抛出异常
         * @throws XmlPullParserException Pull解析器抛出异常
         */
        void startTag(XmlPullParser parser) throws IOException, XmlPullParserException;

        /**
         * 退出标签
         *
         * @param parser parser.getName()获取标签名
         */
        void endTag(XmlPullParser parser);
    }
}
