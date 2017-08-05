package cesc.shang.demo.examples.proxy;

import android.os.Parcel;
import android.support.annotation.NonNull;

import cesc.shang.baselib.base.entity.BaseEntity;
import cesc.shang.baselib.support.BaseContextSupport;
import cesc.shang.utilslib.utils.debug.LogUtils;

/**
 * Created by shanghaolongteng on 2017/8/5.
 */

public class ProxyEntity extends BaseEntity implements Comparable {
    private LogUtils mLog;
    private int mId;

    public ProxyEntity(BaseContextSupport support, int id) {
        mLog = support.getUtilsManager().getLogUtils(getClass().getSimpleName());
        this.mId = id;
    }

    public int getId() {
        return mId;
    }

    @Override
    public int compareTo(Object o) {
        mLog.i("compareTo");
        return 0;
    }

    @Override
    public String toString() {
        return "mId : " + mId;
//        return super.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
    }

    public ProxyEntity() {
    }

    protected ProxyEntity(Parcel in) {
        this.mId = in.readInt();
    }

    public static final Creator<ProxyEntity> CREATOR = new Creator<ProxyEntity>() {
        @Override
        public ProxyEntity createFromParcel(Parcel source) {
            return new ProxyEntity(source);
        }

        @Override
        public ProxyEntity[] newArray(int size) {
            return new ProxyEntity[size];
        }
    };
}
