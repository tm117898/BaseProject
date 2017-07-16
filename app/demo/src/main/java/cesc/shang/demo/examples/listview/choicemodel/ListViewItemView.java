package cesc.shang.demo.examples.listview.choicemodel;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.LinearLayout;

/**
 * Created by shanghaolongteng on 2016/7/15.
 */
public class ListViewItemView extends LinearLayout implements Checkable {
    public ListViewItemView(Context context) {
        super(context);
    }

    public ListViewItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setChecked(boolean checked) {
        ListViewChoiceModelActivityListAdapter.VH vh = (ListViewChoiceModelActivityListAdapter.VH) getTag();
        vh.mCheckBox.setChecked(checked);
    }

    @Override
    public boolean isChecked() {
        ListViewChoiceModelActivityListAdapter.VH vh = (ListViewChoiceModelActivityListAdapter.VH) getTag();
        return vh.mCheckBox.isChecked();
    }

    @Override
    public void toggle() {
        ListViewChoiceModelActivityListAdapter.VH vh = (ListViewChoiceModelActivityListAdapter.VH) getTag();
        vh.mCheckBox.toggle();
    }
}
