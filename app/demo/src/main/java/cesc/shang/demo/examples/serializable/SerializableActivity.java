package cesc.shang.demo.examples.serializable;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cesc.shang.demo.R;
import cesc.shang.demo.base.DemoBaseActivity;
import cesc.shang.utilslib.utils.file.SerializableUtils;

/**
 * Created by shanghaolongteng on 2016/7/17.
 */
public class SerializableActivity extends DemoBaseActivity {
    private String TEST_FILE_PATH;

    @BindView(R.id.text)
    TextView mText;
    @BindView(R.id.write_button)
    Button mWriteButton;
    @BindView(R.id.read_button)
    Button mReadButton;

    private SerializableEntity entity = null;
    private int mIndex = 0;

    @Override
    public int getContentViewId() {
        return R.layout.serializable_activity_layout;
    }

    @Override
    public void initData() {
        TEST_FILE_PATH = getUtilsManager().getFileUtils().getSdCardCatchPath(this) + "/SerializableActivityTextFile.txt";
    }

    @OnClick({R.id.write_button, R.id.read_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.write_button:
                entity = new SerializableEntity();
                entity.setIndex(mIndex);

                getSerializableUtils().writeObject(TEST_FILE_PATH, entity);
                showToast("mIndex : " + mIndex);

                mIndex++;
                break;
            case R.id.read_button:
                entity = (SerializableEntity) getSerializableUtils().readObject(TEST_FILE_PATH);
                mText.setText(entity.toString());
                break;
        }
    }

    private SerializableUtils getSerializableUtils() {
        return getUtilsManager().getSerializableUtils();
    }
}
