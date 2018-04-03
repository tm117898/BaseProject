package cesc.shang.baselib.base.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.support.context.INotContextSupport;
import cesc.shang.baselib.support.manager.HandlerManager;
import cesc.shang.baselib.support.manager.UtilsManager;
import cesc.shang.utilslib.utils.debug.LogUtils;

/**
 * Created by shanghaolongteng on 2016/8/10.
 */
public abstract class BaseAppWidgetProvider extends AppWidgetProvider implements INotContextSupport {
    protected LogUtils mLog;

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        mLog.i("onEnabled");
        onEnable(context);
    }

    /**
     * 当该窗口小部件第一次添加到桌面时调用该方法，可添加多次但只第一次调用
     *
     * @param context 上下文
     */
    protected abstract void onEnable(Context context);

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        mLog.i("onUpdate");

        refreshWidgets(context, appWidgetManager, appWidgetIds);
    }

    /**
     * 刷新全部插件
     *
     * @param context          上下文
     * @param appWidgetManager AppWidgetManager
     * @param appWidgetIds     插件id集合
     */
    private void refreshWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        int count = appWidgetIds.length;
        for (int i = 0; i < count; i++) {
            appWidgetManager.updateAppWidget(appWidgetIds[i], onUpdateWidgetItem(context, appWidgetIds[i]));
        }
    }

    /**
     * widget刷新
     *
     * @param context 上下文
     * @param id      widget的id
     * @return RemoteViews
     */
    protected abstract RemoteViews onUpdateWidgetItem(Context context, int id);

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        mLog.i("onDisabled");
        onDisable(context);
    }

    /**
     * 当最后一个该窗口小部件删除时调用该方法，注意是最后一个
     *
     * @param context 上下文
     */
    protected abstract void onDisable(Context context);

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        mLog.i("onDeleted");
        onDelete(context, appWidgetIds);
    }

    /**
     * 每删除一次窗口小部件就调用一次
     *
     * @param context      上下文
     * @param appWidgetIds 删除的id
     */
    protected abstract void onDelete(Context context, int[] appWidgetIds);

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        mLog = getUtilsManager(context).getLogUtils(this.getClass().getSimpleName());
        mLog.i("onReceive , intent : " + intent);
        onReceiving(context, intent);
    }

    /**
     * 接收窗口小部件事件时发送的广播
     *
     * @param context 上下文
     * @param intent  Intent
     */
    protected abstract void onReceiving(Context context, Intent intent);

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId,
                                          Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        mLog.i("onAppWidgetOptionsChanged");
    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
        mLog.i("onRestored");
    }

    /**
     * 获取全部的插件id
     *
     * @param context 上下文
     * @return 全部插件id
     */
    public int[] getAppWidgetIds(Context context) {
        AppWidgetManager manager = (AppWidgetManager) context.getSystemService(Context.APPWIDGET_SERVICE);
        return manager.getAppWidgetIds(new ComponentName(context, getClass()));
    }

    /**
     * 刷新全部插件
     *
     * @param context 上下文
     */
    public void refreshAllWidget(Context context) {
        int[] ids = getAppWidgetIds(context);
        refreshWidgets(context, (AppWidgetManager) context.getSystemService(Context.APPWIDGET_SERVICE), ids);
    }

    @Override
    public BaseApplication getApp(Context context) {
        return (BaseApplication) context.getApplicationContext();
    }

    @Override
    public HandlerManager getHandlerManager(Context context) {
        return getApp(context).getHandlerManager();
    }

    @Override
    public UtilsManager getUtilsManager(Context context) {
        return getApp(context).getUtilsManager();
    }
}
