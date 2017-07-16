package cesc.shang.demo.examples.listview.choicemodel;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cesc.shang.demo.R;
import cesc.shang.demo.base.DemoBaseActivity;
import cesc.shang.demo.examples.listview.anim.ListEntity;

/**
 * Created by shanghaolongteng on 2016/7/15.
 */
public class ListViewChoiceModelActivity extends DemoBaseActivity {
    @BindView(R.id.list_view)
    ListView mListView;
    private ListViewChoiceModelActivityListAdapter mAdapter = null;

    @Override
    public int getContentViewId() {
        return R.layout.list_view_choice_model_activty_layout;
    }

    @Override
    public void setAdapter() {
        mAdapter = new ListViewChoiceModelActivityListAdapter(this);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        new Thread() {
            @Override
            public void run() {
                super.run();

                final List<ListEntity> list = new ArrayList<ListEntity>();
                for (int i = 0; i < 11; i++) {
                    list.add(new ListEntity("shlt -- > " + i));
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setList(list);
                    }
                });
            }
        }.start();
    }
}
