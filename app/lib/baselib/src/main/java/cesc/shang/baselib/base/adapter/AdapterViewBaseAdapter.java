package cesc.shang.baselib.base.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import cesc.shang.baselib.support.context.IContextSupport;
import cesc.shang.utilslib.utils.debug.LogUtils;

/**
 * Created by shanghaolongteng on 2016/7/14.
 *
 * @param <T> 数据类泛型
 * @param <U> ViewHolder泛型
 */
public abstract class AdapterViewBaseAdapter<T, U extends AdapterViewBaseAdapter.ViewHolder> extends BaseAdapter {
    public static final int ITEM_VIEW_LAYOUT_INVALID_ID = 0;
    protected LogUtils mLog;
    private List<T> mList = new ArrayList<>();

    public AdapterViewBaseAdapter(IContextSupport support) {
        mLog = support.getUtilsManager().getLogUtils(this.getClass().getSimpleName());
    }

    /**
     * 设置新数据并刷新界面
     *
     * @param list 新数据集合
     */
    public void setList(List<T> list) {
        mList.clear();
        appendList(list);
    }

    /**
     * 追加数据并刷新界面
     *
     * @param list 追加数据集合
     */
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
        U u;
        if (convertView == null) {
            int layoutId = getViewLayoutId(viewType);
            if (layoutId == ITEM_VIEW_LAYOUT_INVALID_ID) {
                convertView = getViewLayout(parent, viewType);
            } else {
                convertView = LayoutInflater.from(parent.getContext()).inflate(layoutId, null, false);
            }
            u = getViewHolder(convertView, viewType);
            convertView.setTag(u);
            onConvertViewCreate(convertView, viewType);
        } else {
            u = (U) convertView.getTag();
        }

        T t = mList.get(position);
        u.setEntity(t);
        u.setPosition(position);
        u.setViewType(viewType);

        bindData(t, u);

        return convertView;
    }

    /**
     * 缓存View被新创建时调用
     *
     * @param convertView 缓存View
     * @param viewType    缓存类型
     */
    public void onConvertViewCreate(View convertView, int viewType) {
    }

    /**
     * 返回ItemView布局id
     *
     * @param viewType 缓存类型
     * @return ItemView
     */
    public abstract int getViewLayoutId(int viewType);

    /**
     * 当getViewLayoutId返回ITEM_VIEW_LAYOUT_INVALID_ID时，重写此方法直接返回View
     *
     * @param parent   ListView
     * @param viewType 缓存类型
     * @return ItemView
     */
    protected View getViewLayout(ViewGroup parent, int viewType) {
        return null;
    }

    /**
     * 创建缓存View的Holder
     *
     * @param convertView 缓存View
     * @param viewType    缓存类型
     * @return ViewHolder
     */
    public abstract U getViewHolder(View convertView, int viewType);

    /**
     * 绑定数据到item
     *
     * @param t 数据
     * @param u item的Holder
     */
    public abstract void bindData(T t, U u);

    /**
     * ViewHolder基类
     *
     * @param <T> 数据类型
     */
    public static class ViewHolder<T> {
        private T entity;
        private int mPosition;
        private int mViewType;

        public T getEntity() {
            return entity;
        }

        public void setEntity(T entity) {
            this.entity = entity;
        }

        public int getPosition() {
            return mPosition;
        }

        public void setPosition(int position) {
            this.mPosition = position;
        }

        public int getViewType() {
            return mViewType;
        }

        public void setViewType(int viewType) {
            this.mViewType = viewType;
        }

    }
}
