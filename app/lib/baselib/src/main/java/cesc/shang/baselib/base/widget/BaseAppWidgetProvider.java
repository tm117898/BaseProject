package cesc.shang.baselib.base.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.support.INotContextSupport;
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

    protected abstract void onEnable(Context context);

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        mLog.i("onUpdate");

        refreshWidgets(context, appWidgetManager, appWidgetIds);
    }

    private void refreshWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        int count = appWidgetIds.length;
        for (int i = 0; i < count; i++) {
            appWidgetManager.updateAppWidget(appWidgetIds[i], onUpdateWidgetItem(context, appWidgetIds[i]));
        }
    }

    protected abstract RemoteViews onUpdateWidgetItem(Context context, int id);

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        mLog.i("onDisabled");
        onDisable(context);
    }

    protected abstract void onDisable(Context context);

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        mLog.i("onDeleted");
        onDelete(context, appWidgetIds);
    }

    protected abstract void onDelete(Context context, int[] appWidgetIds);

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        mLog = getUtilsManager(context).getLogUtils(this.getClass().getSimpleName());
        mLog.i("onReceive , intent : " + intent);
        onReceiving(context, intent);
    }

    protected abstract void onReceiving(Context context, Intent intent);

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        mLog.i("onAppWidgetOptionsChanged");
    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
        mLog.i("onRestored");
    }

    public int[] getAppWidgetIds(Context context) {
        AppWidgetManager manager = (AppWidgetManager) context.getSystemService(Context.APPWIDGET_SERVICE);
        return manager.getAppWidgetIds(new ComponentName(context, getClass()));
    }

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
