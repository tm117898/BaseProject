package cesc.shang.demo.examples.proxy;

import java.util.Arrays;
import java.util.Random;

import cesc.shang.demo.R;
import cesc.shang.demo.base.DemoBaseActivity;

/**
 * Created by shanghaolongteng on 2017/8/5.
 */

public class ProxyActivity extends DemoBaseActivity {
    @Override
    public int getContentViewId() {
        return R.layout.proxy_activity_layout;
    }

    @Override
    public void initData() {
        super.initData();

        final int length = 10;
        Random r = new Random();

        Object[] arrays = new Object[length];
        for (int i = 0; i < length; i++) {
            int id = r.nextInt(length);
            ProxyEntity e = new ProxyEntity(this, id);
            ProxyHandler handle = new ProxyHandler(getApp());
            Object proxy = handle.proxy(e);
            arrays[i] = proxy;
        }

        Arrays.sort(arrays);
        mLog.i(Arrays.toString(arrays));
    }
}
