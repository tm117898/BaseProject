package cesc.shang.baselib.base.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.support.manager.ControllerManager;
import cesc.shang.baselib.support.manager.HandlerManager;
import cesc.shang.baselib.support.manager.UtilsManager;
import cesc.shang.utilslib.utils.debug.LogUtils;

/**
 * Created by shanghaolongteng on 2016/7/31.
 */
public class BaseContentProvider extends ContentProvider implements cesc.shang.baselib.support.IContextSupport {
    protected LogUtils mLog;

    @Override
    public boolean onCreate() {
        mLog = getUtilsManager().getLogUtils(this.getClass().getSimpleName());
        mLog.i("create");
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        mLog.i("create , uri : ", uri,
                " , projection : " + projection,
                " , selection : " + selection,
                " , selectionArgs : " + selectionArgs,
                " , sortOrder : " + sortOrder);
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        mLog.i("create , uri : ", uri);
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        mLog.i("create , uri : ", uri,
                " , values : " + values);
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        mLog.i("create , uri : ", uri,
                " , selection : " + selection,
                " , selectionArgs : " + selectionArgs);
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        mLog.i("create , uri : ", uri,
                " , values : " + values,
                " , selection : " + selection,
                " , selectionArgs : " + selectionArgs);
        return 0;
    }

    @Override
    public BaseApplication getApp() {
        return (BaseApplication) getContext().getApplicationContext();
    }

    @Override
    public ControllerManager getControllerManager() {
        return getApp().getControllerManager();
    }

    @Override
    public HandlerManager getHandlerManager() {
        return getApp().getHandlerManager();
    }

    @Override
    public UtilsManager getUtilsManager() {
        return getApp().getUtilsManager();
    }
}
