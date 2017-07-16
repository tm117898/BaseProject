package cesc.shang.demo.examples.serializable;

import java.io.Serializable;

/**
 * Created by shanghaolongteng on 2016/7/17.
 */
public class SerializableEntity implements Serializable {
    private String mName = "shlt";
    private int mAge = 27;
    private boolean isMale = true;
    private int index = 0;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmAge() {
        return mAge;
    }

    public void setmAge(int mAge) {
        this.mAge = mAge;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("index : ");
        builder.append(index);
        builder.append("----");

        builder.append("mAge : ");
        builder.append(mAge);
        builder.append("----");

        builder.append("mName : ");
        builder.append(mName);
        builder.append("----");

        builder.append("isMale : ");
        builder.append(isMale);
        builder.append("----");

        return builder.toString();
//        return super.toString();
    }
}
