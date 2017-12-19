package cesc.shang.demo.examples.customcursor;

import android.database.Cursor;
import android.net.Uri;
import android.widget.TextView;

import java.util.Arrays;

import butterknife.BindView;
import cesc.shang.baselib.base.activity.BaseActivity;
import cesc.shang.demo.R;

/**
 * Created by Cesc Shang on 2017/12/19.
 */

public class CustomCursorActivity extends BaseActivity {
    @BindView(R.id.message_text)
    TextView mMessageText;

    @Override
    public int getContentViewId() {
        return R.layout.custom_cursor_activity_layout;
    }

    @Override
    public void initData() {
        super.initData();
        StringBuilder sb = new StringBuilder();

        try (Cursor c = getContentResolver().query(Uri.parse("content://" + CustomCursorProvider.AUTHORITY),
                null, null, null, null)) {
            final String[] columnNames = c.getColumnNames();
            mLog.i("columnNames : " + Arrays.toString(columnNames));

            final int columnNamesLength = columnNames.length;
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                for (int i = 0; i < columnNamesLength; i++) {
                    String columnName = columnNames[i];
                    String value = c.getString(c.getColumnIndex(columnName));

                    sb.append(columnName).append(" : ").append(value).append("\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String text = sb.toString();
        sb.setLength(0);
        mLog.i("text : " + text);
        mMessageText.setText(text);
    }
}
