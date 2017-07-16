package cesc.shang.demo.examples.ipc.messenger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cesc.shang.demo.R;
import cesc.shang.demo.base.DemoBaseActivity;

/**
 * Created by shanghaolongteng on 2016/7/17.
 */
public class MessengerActivity extends DemoBaseActivity {
    @BindView(R.id.text)
    TextView mText;
    @BindView(R.id.send_button)
    Button mSendButton;

    private Messenger mService = null;

    @Override
    public int getContentViewId() {
        return R.layout.messenger_activity_layout;
    }

    @Override
    public void initData() {
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = new Messenger(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mService = null;
            }
        }, Context.BIND_AUTO_CREATE);
    }

    private Messenger mClient = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int result = msg.getData().getInt("shlt");
            mLog.i("MessengerActivity , handleMessage , result : ", result);

            mText.setText(result + "");
        }
    });

    @OnClick(R.id.send_button)
    public void onClick() {
        Message message = getUtilsManager().getMessageUtils().getMessage();
        Bundle bundle = new Bundle();
        bundle.putInt("shlt", 7);//Parcelable对象不能传递！看来只能基本类型……
        message.setData(bundle);
        message.replyTo = mClient;
        try {
            mService.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
