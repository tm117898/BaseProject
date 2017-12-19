package cesc.shang.demo.examples.main;

import java.util.ArrayList;
import java.util.List;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.support.callback.ISuccessCallBack;
import cesc.shang.baselib.support.manager.base.BaseController;
import cesc.shang.demo.examples.activitywwitchorder.ActivitySwitchOrderActivity1;
import cesc.shang.demo.examples.animation.AnimationActivity;
import cesc.shang.demo.examples.customcursor.CustomCursorActivity;
import cesc.shang.demo.examples.customxmlarrt.CustomXmlArrtActivity;
import cesc.shang.demo.examples.drawable.DrawableActivity;
import cesc.shang.demo.examples.ipc.aidl.AIDLActivity;
import cesc.shang.demo.examples.ipc.binderconnectionpool.BinderActivity;
import cesc.shang.demo.examples.ipc.messenger.MessengerActivity;
import cesc.shang.demo.examples.ipc.socket.SocketActivity;
import cesc.shang.demo.examples.listview.anim.ListViewAnimActivity;
import cesc.shang.demo.examples.listview.choicemodel.ListViewChoiceModelActivity;
import cesc.shang.demo.examples.notify.NotifyActivity;
import cesc.shang.demo.examples.okhttp.OkHttpActivity;
import cesc.shang.demo.examples.proxy.ProxyActivity;
import cesc.shang.demo.examples.reflect.ReflectActivity;
import cesc.shang.demo.examples.serializable.SerializableActivity;
import cesc.shang.demo.examples.switchview.SwitchViewActivity;
import cesc.shang.demo.examples.textmethod.TextMethodActivity;
import cesc.shang.demo.examples.view.conflict.exteriorintercept.ExteriorInterceptActivity;
import cesc.shang.demo.examples.view.event.TouchEventActivity;
import cesc.shang.demo.examples.view.move.TouchMoveActivity;
import cesc.shang.demo.examples.window.WindowActivity;

/**
 * Created by shanghaolongteng on 2017/7/16.
 */

public class MainController extends BaseController {
    public MainController(BaseApplication app) {
        super(app);
    }

    @Override
    public void onDestroy() {

    }

    public void initData(final ISuccessCallBack<List<MainActivityListEntity>> callBack) {
        new Thread() {
            @Override
            public void run() {
                super.run();

                final List<MainActivityListEntity> list = new ArrayList<>();

                list.add(new MainActivityListEntity("自定义Cursor示例", CustomCursorActivity.class));
                list.add(new MainActivityListEntity("代理示例", ProxyActivity.class));
                list.add(new MainActivityListEntity("反射分析示例", ReflectActivity.class));
                list.add(new MainActivityListEntity("OkHttp示例", OkHttpActivity.class));
                list.add(new MainActivityListEntity("悬浮窗示例", WindowActivity.class));
                list.add(new MainActivityListEntity("Animation示例", AnimationActivity.class));
                list.add(new MainActivityListEntity("Drawable示例", DrawableActivity.class));
                list.add(new MainActivityListEntity("通知简易示例", NotifyActivity.class));
                list.add(new MainActivityListEntity("滑动冲突外部拦截法", ExteriorInterceptActivity.class));
                list.add(new MainActivityListEntity("View的事件传递", TouchEventActivity.class));
                list.add(new MainActivityListEntity("View的基本操作", TouchMoveActivity.class));
                list.add(new MainActivityListEntity("使用binder连接池", BinderActivity.class));
                list.add(new MainActivityListEntity("使用socket进行IPC", SocketActivity.class));
                list.add(new MainActivityListEntity("使用aidl进行IPC", AIDLActivity.class));
                list.add(new MainActivityListEntity("使用Messenger进行IPC", MessengerActivity.class));
                list.add(new MainActivityListEntity("Serializable对象文件序列化", SerializableActivity.class));
                list.add(new MainActivityListEntity("前后Activity启动的生命周期顺序", ActivitySwitchOrderActivity1.class));
                list.add(new MainActivityListEntity("ListView单选模式", ListViewChoiceModelActivity.class));
                list.add(new MainActivityListEntity("文字样式技巧", TextMethodActivity.class));
                list.add(new MainActivityListEntity("ListView瀑布流进入", ListViewAnimActivity.class));
                list.add(new MainActivityListEntity("TextSwitch和ImageSwitch", SwitchViewActivity.class));
                list.add(new MainActivityListEntity("自定义xml属性", CustomXmlArrtActivity.class));

                callBack.onSuccess(list);
            }
        }.start();
    }
}
