package cesc.shang.demo.examples.textmethod;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import butterknife.BindView;
import cesc.shang.demo.R;
import cesc.shang.demo.base.DemoBaseActivity;
import cesc.shang.utilslib.utils.widget.ViewUtils;

/**
 * Created by shanghaolongteng on 2016/7/15.
 */
public class TextMethodActivity extends DemoBaseActivity {
    @BindView(R.id.html_text)
    TextView mHtmlText;
    @BindView(R.id.color_text)
    TextView mColorText;
    @BindView(R.id.font_text)
    TextView mFontText;
    @BindView(R.id.shadow_text)
    TextView mShadowText;

    @Override
    public int getContentViewId() {
        return R.layout.text_mothod_activity_layout;
    }

    @Override
    public void setupView() {
        super.setupView();
        getViewUtils().setHtmlText(mHtmlText, "打开 <a href=\"http://www.baidu.com/\">百度啊百度</href>");

        Spannable spannable = new SpannableString("的回复就爱上飞机都十分骄傲是分开多久萨芬好看了符合");
        spannable.setSpan(new BackgroundColorSpan(Color.RED), 0, 3, 0);
        spannable.setSpan(new BackgroundColorSpan(Color.YELLOW), 6, 9, 0);
        spannable.setSpan(new BackgroundColorSpan(Color.BLUE), 11, 15, 0);
        spannable.setSpan(new ForegroundColorSpan(Color.GREEN), 3, 6, 0);
        spannable.setSpan(new ForegroundColorSpan(Color.GREEN), 8, 14, 0);
        mColorText.setText(spannable);

        mFontText.setText("等你回复噶开机速度还是的世界航空");
        getViewUtils().setTypeface(this, "text_method_activity_text_font.TTF", mFontText);

        mShadowText.setText("打卡上贷款及罚款了附近的喀什发达到");
        mShadowText.setShadowLayer(0.5F, 0, 0, Color.RED);
    }

    private ViewUtils getViewUtils() {
        return getUtilsManager().getViewUtils();
    }
}
