package cesc.shang.demo.examples.ipc.aidl;

import android.os.Parcel;
import android.os.Parcelable;

import cesc.shang.baselib.base.entity.BaseEntity;

/**
 * Created by shanghaolongteng on 2016/7/31.
 */
public class AIDLEntity extends BaseEntity {
    private int age;
    private String name;
    private boolean isMale;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    @Override
    public String toString() {
//        return super.toString();
        return "age : " + age + " , name : " + name + " , isMale : " + isMale;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.age);
        dest.writeString(this.name);
        dest.writeByte(this.isMale ? (byte) 1 : (byte) 0);
    }

    public AIDLEntity() {
    }

    protected AIDLEntity(Parcel in) {
        this.age = in.readInt();
        this.name = in.readString();
        this.isMale = in.readByte() != 0;
    }

    public static final Parcelable.Creator<AIDLEntity> CREATOR = new Parcelable.Creator<AIDLEntity>() {
        @Override
        public AIDLEntity createFromParcel(Parcel source) {
            return new AIDLEntity(source);
        }

        @Override
        public AIDLEntity[] newArray(int size) {
            return new AIDLEntity[size];
        }
    };
}
