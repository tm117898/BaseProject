package cesc.shang.demo.examples.listview.anim;

import android.os.Parcel;

import cesc.shang.baselib.base.entity.BaseEntity;

/**
 * Created by shanghaolongteng on 2016/7/15.
 */
public class ListEntity extends BaseEntity {
    private String text;

    public ListEntity(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.text);
    }

    protected ListEntity(Parcel in) {
        this.text = in.readString();
    }

    public static final Creator<ListEntity> CREATOR = new Creator<ListEntity>() {
        @Override
        public ListEntity createFromParcel(Parcel source) {
            return new ListEntity(source);
        }

        @Override
        public ListEntity[] newArray(int size) {
            return new ListEntity[size];
        }
    };
}
