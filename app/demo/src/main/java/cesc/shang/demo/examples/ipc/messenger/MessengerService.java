package cesc.shang.demo.examples.ipc.messenger;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import cesc.shang.baselib.base.service.BaseService;

/**
 * Created by shanghaolongteng on 2016/7/17.
 */
public class MessengerService extends BaseService {

    private Messenger mMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int data = msg.getData().getInt("shlt");
            mLog.i("MessengerService , handleMessage , data : ", data);

            Messenger client = msg.replyTo;
            Message message = getUtilsManager().getMessageUtils().getMessage();
            Bundle bundle = new Bundle();
            bundle.putInt("shlt", 11);//Parcelable对象不能传递！看来只能基本类型……
            message.setData(bundle);
            try {
                client.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    });

    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        return mMessenger.getBinder();
    }

}
