package cesc.shang.demo.examples.okhttp;

import android.os.Parcel;

import cesc.shang.baselib.base.entity.BaseEntity;

/**
 * Created by Cesc Shang on 2017/7/17.
 */

public class OkHttpEntity extends BaseEntity {

    /**
     * lon : 117.12
     * level : 2
     * address :
     * cityName :
     * alevel : 4
     * lat : 36.65121
     */

    private double lon;
    private int level;
    private String address;
    private String cityName;
    private int alevel;
    private double lat;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("lon : ").append(lon).append(" , ");
        builder.append("level : ").append(level).append(" , ");
        builder.append("address : ").append(address).append(" , ");
        builder.append("alevel : ").append(alevel).append(" , ");
        builder.append("lat : ").append(lat).append(" , ");
        return builder.toString();
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getAlevel() {
        return alevel;
    }

    public void setAlevel(int alevel) {
        this.alevel = alevel;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.lon);
        dest.writeInt(this.level);
        dest.writeString(this.address);
        dest.writeString(this.cityName);
        dest.writeInt(this.alevel);
        dest.writeDouble(this.lat);
    }

    public OkHttpEntity() {
    }

    protected OkHttpEntity(Parcel in) {
        this.lon = in.readDouble();
        this.level = in.readInt();
        this.address = in.readString();
        this.cityName = in.readString();
        this.alevel = in.readInt();
        this.lat = in.readDouble();
    }

    public static final Creator<OkHttpEntity> CREATOR = new Creator<OkHttpEntity>() {
        @Override
        public OkHttpEntity createFromParcel(Parcel source) {
            return new OkHttpEntity(source);
        }

        @Override
        public OkHttpEntity[] newArray(int size) {
            return new OkHttpEntity[size];
        }
    };
}
