package cesc.shang.baselib.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cesc.shang.baselib.support.context.IContextSupport;
import cesc.shang.utilslib.utils.debug.LogUtils;


/**
 * Created by shanghaolongteng on 2016/8/8.
 *
 * @param <T> 数据类泛型
 * @param <U> ViewHolder泛型
 */
public abstract class RecyclerViewBaseAdapter<T, U extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<U> {
    public static final int ITEM_VIEW_LAYOUT_INVALID_ID = 0;
    protected LogUtils mLog;
    private List<T> mList = new ArrayList<>();

    public RecyclerViewBaseAdapter(IContextSupport support) {
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
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public U onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView;
        int layoutId = getViewLayoutId(viewType);
        if (layoutId == ITEM_VIEW_LAYOUT_INVALID_ID) {
            convertView = getViewLayout(parent, viewType);
        } else {
            convertView = LayoutInflater.from(parent.getContext()).inflate(layoutId, null, false);
        }
        onConvertViewCreate(convertView, viewType);
        return getViewHolder(convertView, viewType);
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
     * 缓存View被新创建时调用
     *
     * @param convertView 缓存View
     * @param viewType    缓存类型
     */
    public void onConvertViewCreate(View convertView, int viewType) {
    }

    @Override
    public void onBindViewHolder(U holder, int position) {
        T t = mList.get(position);
        bindData(t, holder);
    }

    /**
     * 绑定数据到item
     *
     * @param t 数据
     * @param u item的Holder
     */
    public abstract void bindData(T t, U u);
}
