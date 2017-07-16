package cesc.shang.demo.examples.listview.choicemodel;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import cesc.shang.baselib.base.adapter.AdapterViewBaseAdapter;
import cesc.shang.baselib.support.BaseContextSupport;
import cesc.shang.demo.R;
import cesc.shang.demo.examples.listview.anim.ListEntity;

/**
 * Created by shanghaolongteng on 2016/7/15.
 */
public class ListViewChoiceModelActivityListAdapter extends AdapterViewBaseAdapter<ListEntity, ListViewChoiceModelActivityListAdapter.VH> {

    public ListViewChoiceModelActivityListAdapter(BaseContextSupport support) {
        super(support);
    }

    @Override
    public int getViewLayoutId(int viewType) {
        return R.layout.list_view_choice_model_activty_list_item;
    }

    @Override
    public VH getViewHolder(View convertView, int viewType) {
        VH vh = new VH();
        vh.mText = convertView.findViewById(R.id.text);
        vh.mCheckBox = convertView.findViewById(R.id.checkbox);
        return vh;
    }

    @Override
    public void bindData(ListEntity entity, VH vh, int viewType) {
        vh.mText.setText(entity.getText());
    }

    public static class VH extends AdapterViewBaseAdapter.ViewHolder<ListEntity> {
        public TextView mText = null;
        public CheckBox mCheckBox = null;
    }
}