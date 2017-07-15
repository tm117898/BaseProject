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

    public void setHtmlText(TextView view, String text) {
        if (!TextUtils.isEmpty(text)) {
            view.setText(Html.fromHtml(text));
            view.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    public void setTypeface(Context context, String fileName, TextView view) {
        AssetManager as = context.getAssets();
        Typeface tf = Typeface.createFromAsset(as, fileName);
        view.setTypeface(tf);
    }

    public int getScaledTouchSlop(Context context) {
        return ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void setBackground(View view, Drawable drawable) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(drawable);
        } else {
            view.setBackground(drawable);
        }
    }
}
