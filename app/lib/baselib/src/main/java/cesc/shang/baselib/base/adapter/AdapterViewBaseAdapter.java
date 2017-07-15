package cesc.shang.baselib.base.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import cesc.shang.baselib.support.BaseContextSupport;
import cesc.shang.utilslib.utils.debug.LogUtils;

/**
 * Created by shanghaolongteng on 2016/7/14.
 */
public abstract class AdapterViewBaseAdapter<T, E extends AdapterViewBaseAdapter.ViewHolder> extends BaseAdapter {
    public static final int ITEM_VIEW_LAYOUT_INVALID_ID = 0;
    protected LogUtils mLog;
    private List<T> mList = new ArrayList<>();

    public AdapterViewBaseAdapter(BaseContextSupport support) {
        mLog = support.getUtilsManager().getLogUtils(this.getClass().getSimpleName());
    }

    public void setList(List<T> list) {
        mList.clear();
        appendList(list);
    }

    public void appendList(List<T> list) {
        if (list != null && list.size() > 0) {
            mList.addAll(list);
        }
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        E e;
        if (convertView == null) {
            int layoutId = getViewLayoutId(viewType);
            if (layoutId == ITEM_VIEW_LAYOUT_INVALID_ID) {
                convertView = getViewLayout(parent, viewType);
            } else {
                convertView = LayoutInflater.from(parent.getContext()).inflate(layoutId, null, false);
            }
            e = getViewHolder(convertView, viewType);
            convertView.setTag(e);
            onConvertViewCreate(convertView, viewType);
        } else {
            e = (E) convertView.getTag();
        }

        T t = mList.get(position);
        e.entity = t;

        bindData(t, e, viewType);

        return convertView;
    }

    public void onConvertViewCreate(View convertView, int viewType) {
    }

    public abstract int getViewLayoutId(int viewType);

    protected View getViewLayout(ViewGroup parent, int viewType) {
        return null;
    }

    public abstract E getViewHolder(View convertView, int viewType);

    public abstract void bindData(T t, E e, int viewType);

    public static class ViewHolder<T> {
        public T entity;
    }
}
