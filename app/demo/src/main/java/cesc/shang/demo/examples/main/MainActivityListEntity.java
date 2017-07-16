package cesc.shang.demo.examples.main;

/**
 * Created by shanghaolongteng on 2016/7/14.
 */
public class MainActivityListEntity {
    private String text;
    private Class<?> activityClass;

    public MainActivityListEntity(String text, Class<?> activityClass) {
        this.text = text;
        this.activityClass = activityClass;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Class<?> getActivityClass() {
        return activityClass;
    }

    public void setActivityClass(Class<?> activityClass) {
        this.activityClass = activityClass;
    }
}
