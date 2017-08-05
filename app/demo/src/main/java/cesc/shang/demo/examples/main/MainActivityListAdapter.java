package cesc.shang.demo.examples.main;

import android.view.View;
import android.widget.TextView;

import cesc.shang.baselib.base.adapter.AdapterViewBaseAdapter;
import cesc.shang.baselib.support.BaseContextSupport;
import cesc.shang.demo.R;

/**
 * Created by shanghaolongteng on 2016/7/14.
 */
public class MainActivityListAdapter extends AdapterViewBaseAdapter<MainActivityListEntity, MainActivityListAdapter.VH> {

    public MainActivityListAdapter(BaseContextSupport support) {
        super(support);
    }

    @Override
    public int getViewLayoutId(int viewType) {
        return R.layout.main_layout_list_item;
    }

    @Override
    public VH getViewHolder(View convertView, int viewType) {
        VH vh = new VH();
        vh.mText = (TextView) convertView.findViewById(R.id.text);
        return vh;
    }

    @Override
    public void bindData(MainActivityListEntity entity, VH vh, int viewType) {
        vh.mText.setText(entity.getText());
    }

    public static class VH extends AdapterViewBaseAdapter.ViewHolder<MainActivityListEntity> {
        public TextView mText = null;
    }
}
