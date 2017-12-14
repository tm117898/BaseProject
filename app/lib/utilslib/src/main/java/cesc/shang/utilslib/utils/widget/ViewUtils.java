package cesc.shang.utilslib.utils.widget;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.TextView;

/**
 * Created by shanghaolongteng on 2016/7/15.
 */
public class ViewUtils {
    public ViewUtils() {
    }

    /**
     * 将文字以HTML格式设置到TextView
     *
     * @param view TextView
     * @param text 文字
     */
    public void setHtmlText(TextView view, String text) {
        if (!TextUtils.isEmpty(text)) {
            view.setText(Html.fromHtml(text));
            view.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    /**
     * 设置TextView字体
     *
     * @param context  上下文
     * @param fileName 字体绝对路径
     * @param view     待设置的View
     */
    public void setTypeface(Context context, String fileName, TextView view) {
        AssetManager as = context.getAssets();
        Typeface tf = Typeface.createFromAsset(as, fileName);
        view.setTypeface(tf);
    }

    /**
     * 获取Touch Move 临界值
     *
     * @param context 上下文
     * @return 临界值，超过次值认为是Move
     */
    public int getScaledTouchSlop(Context context) {
        return ViewConfiguration.get(context).getScaledTouchSlop();
    }

    /**
     * 设置View背景
     *
     * @param view     待设置的View
     * @param drawable 背景图片
     */
    public void setBackground(View view, Drawable drawable) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(drawable);
        } else {
            view.setBackground(drawable);
        }
    }
}
