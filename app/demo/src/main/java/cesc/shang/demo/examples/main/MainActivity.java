package cesc.shang.demo.examples.main;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.OnItemClick;
import cesc.shang.baselib.support.callback.SuccessCallBack;
import cesc.shang.demo.R;
import cesc.shang.demo.base.DemoBaseActivity;

/**
 * Created by shanghaolongteng on 2017/7/16.
 */

public class MainActivity extends DemoBaseActivity {
    @BindView(R.id.list_view)
    ListView mListView;
    private MainActivityListAdapter mAdapter = null;

    @Override
    public int getContentViewId() {
        return R.layout.main_layout;
    }

    @Override
    public void setupView() {
        mAdapter = new MainActivityListAdapter(this);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        super.initData();
        getControllerManager().getInitController().init();
        getControllerManager().getMainController().initData(new CallBack(this));
    }

    private static class CallBack extends SuccessCallBack<List<MainActivityListEntity>, MainActivity> {
        public CallBack(MainActivity ref) {
            super(ref);
        }

        @Override
        public void success(List<MainActivityListEntity> list, WeakReference<MainActivity> ref) {
            ref.get().refreshList(list);
        }
    }

    private void refreshList(final List<MainActivityListEntity> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.setList(list);
            }
        });
    }

    @OnItemClick(value = {R.id.list_view})
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MainActivityListAdapter.VH vh = (MainActivityListAdapter.VH) view.getTag();

        Intent intent = new Intent(MainActivity.this, vh.getEntity().getActivityClass());
        startActivity(intent);
    }
}
