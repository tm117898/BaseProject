package cesc.shang.demo.examples.listview.anim;

import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cesc.shang.demo.R;
import cesc.shang.demo.base.DemoBaseActivity;

/**
 * Created by shanghaolongteng on 2016/7/15.
 */
public class ListViewAnimActivity extends DemoBaseActivity {
    @BindView(R.id.list_view)
    ListView mListView;
    private ListViewAnimActivityListAdapter mAdapter = null;

    @Override
    public int getContentViewId() {
        return R.layout.list_view_anim_activity_layout;
    }

    @Override
    public void setupView() {
        super.setupView();

        LayoutAnimationController controller = new LayoutAnimationController(AnimationUtils.loadAnimation(this, R.anim.switch_view_in_anim));
        mListView.setLayoutAnimation(controller);

        mAdapter = new ListViewAnimActivityListAdapter(this);
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
