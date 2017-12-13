package cesc.shang.demo.examples.ipc.socket;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cesc.shang.baselib.support.callback.ISuccessCallBack;
import cesc.shang.demo.R;
import cesc.shang.demo.base.DemoBaseActivity;

/**
 * Created by shanghaolongteng on 2016/8/2.
 */
public class SocketActivity extends DemoBaseActivity {
    @BindView(R.id.message_text)
    TextView mMessageText;

    @Override
    public int getContentViewId() {
        return R.layout.socket_activity_layout;
    }

    @Override
    public void initData() {
        getSocketController().onActivityCreate(this);
    }

    private SocketController getSocketController() {
        return getControllerManager().getSocketController();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSocketController().onActivityDestroy(this);
    }

    @OnClick({R.id.user_1_on_line_button, R.id.user_1_off_line_button, R.id.user_1_send_overall_message_button, R.id.user_1_send_user_2_message_button, R.id.user_2_on_line_button, R.id.user_2_off_line_button, R.id.user_2send_overall_message_button, R.id.user_2_send_user_1_message_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_1_on_line_button:
                getSocketController().user1OnLine(mCallBack);
                break;
            case R.id.user_1_off_line_button:
                getSocketController().user1OffLine();
                break;
            case R.id.user_1_send_overall_message_button:
                getSocketController().user1sendMessage("all");
                break;
            case R.id.user_1_send_user_2_message_button:
                getSocketController().user1sendMessage("2");
                break;
            case R.id.user_2_on_line_button:
                getSocketController().user2OnLine(mCallBack);
                break;
            case R.id.user_2_off_line_button:
                getSocketController().user2OffLine();
                break;
            case R.id.user_2send_overall_message_button:
                getSocketController().user2sendMessage("all");
                break;
            case R.id.user_2_send_user_1_message_button:
                getSocketController().user2sendMessage("1");
                break;
        }
    }

    private ISuccessCallBack mCallBack = new ISuccessCallBack<String>() {
        @Override
        public void onSuccess(String s) {
            showMessage(s);
        }
    };

    private void showMessage(final String message) {
        mMessageText.post(new Runnable() {
            @Override
            public void run() {
                CharSequence oldText = mMessageText.getText();
                mMessageText.setText(message + "\n\t");
                mMessageText.append(oldText);
            }
        });
    }
}
