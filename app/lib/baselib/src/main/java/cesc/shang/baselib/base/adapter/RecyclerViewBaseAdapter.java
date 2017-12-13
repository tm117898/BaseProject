package cesc.shang.baselib.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cesc.shang.baselib.support.IContextSupport;
import cesc.shang.utilslib.utils.debug.LogUtils;


/**
 * Created by shanghaolongteng on 2016/8/8.
 */
public abstract class RecyclerViewBaseAdapter<T, U extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<U> {
    protected LogUtils mLog;
    private List<T> mList = new ArrayList<>();

    public RecyclerViewBaseAdapter(IContextSupport support) {
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
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public U onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(getViewLayoutId(viewType), null, false);
        onConvertViewCreate(convertView, viewType);
        return getViewHolder(convertView, viewType);
    }

    public abstract int getViewLayoutId(int viewType);

    public abstract U getViewHolder(View convertView, int viewType);

    public void onConvertViewCreate(View convertView, int viewType) {
    }

    @Override
    public void onBindViewHolder(U holder, int position) {
        T t = mList.get(position);
        bindData(t, holder, getItemViewType(position));
    }

    public abstract void bindData(T t, U u, int viewType);
}
