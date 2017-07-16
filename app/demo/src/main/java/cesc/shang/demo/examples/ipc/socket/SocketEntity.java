package cesc.shang.demo.examples.ipc.socket;


import android.os.Parcel;

import cesc.shang.baselib.base.socket.BaseSocketSendEntity;

/**
 * Created by shanghaolongteng on 2016/8/3.
 */
public class SocketEntity extends BaseSocketSendEntity {
    @Override
    public String convertString() {
        return "SocketEntity";
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public SocketEntity() {
    }

    protected SocketEntity(Parcel in) {
    }

    public static final Creator<SocketEntity> CREATOR = new Creator<SocketEntity>() {
        @Override
        public SocketEntity createFromParcel(Parcel source) {
            return new SocketEntity(source);
        }

        @Override
        public SocketEntity[] newArray(int size) {
            return new SocketEntity[size];
        }
    };
}
