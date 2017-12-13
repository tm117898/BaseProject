package cesc.shang.baselib.base.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.Arrays;
import java.util.List;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.support.INotContextSupport;
import cesc.shang.baselib.support.manager.ControllerManager;
import cesc.shang.baselib.support.manager.HandlerManager;
import cesc.shang.baselib.support.manager.UtilsManager;
import cesc.shang.utilslib.utils.debug.LogUtils;

/**
 * Created by shanghaolongteng on 2016/7/31.
 */
public class BaseSQLiteOpenHelper extends SQLiteOpenHelper implements INotContextSupport {
    protected LogUtils mLog;

    public BaseSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        this(context, name, factory, version, null);
    }

    public BaseSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);

        mLog = getUtilsManager(context).getLogUtils(this.getClass().getSimpleName());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        mLog.i("create");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mLog.i("onUpgrade , oldVersion : " + oldVersion + " , newVersion : " + newVersion);
    }

    public <T> long insert(String sql, BindStatementDataCallBack<T> callBack, T... ts) {
        return insert(sql, callBack, Arrays.asList(ts));
    }

    public <T> long insert(String sql, BindStatementDataCallBack<T> callBack, List<T> ts) {
        return operation(sql, ts, callBack, new StatementOperation() {
            @Override
            public long operation(SQLiteStatement statement) {
                return statement.executeInsert();
            }
        });
    }

    public <T> long updateOrDelete(String sql, BindStatementDataCallBack<T> callBack, T... ts) {
        return updateOrDelete(sql, callBack, Arrays.asList(ts));
    }

    public <T> long updateOrDelete(String sql, BindStatementDataCallBack<T> callBack, List<T> ts) {
        return operation(sql, ts, callBack, new StatementOperation() {
            @Override
            public long operation(SQLiteStatement statement) {
                return statement.executeUpdateDelete();
            }
        });
    }

    private <T> long operation(String sql, List<T> ts, BindStatementDataCallBack<T> callBack, StatementOperation operation) {
        long result = -1;

        SQLiteDatabase db = getWritableDatabase();
        if (db.isReadOnly()) {
            mLog.e("operation , db.isReadOnly()!!!");
        } else {
            SQLiteStatement statement = db.compileStatement(sql);
            db.beginTransaction();
            try {
                int size = ts.size();
                for (int i = 0; i < size; i++) {
                    T t = ts.get(i);
                    callBack.bind(statement, t);
                    result = operation.operation(statement);
                    statement.clearBindings();
                }
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }

        return result;
    }

    interface StatementOperation {
        long operation(SQLiteStatement statement);
    }

    public interface BindStatementDataCallBack<T> {
        void bind(SQLiteStatement statement, T t);
    }

    @Override
    public BaseApplication getApp(Context context) {
        return (BaseApplication) context.getApplicationContext();
    }

    @Override
    public ControllerManager getControllerManager(Context context) {
        return getApp(context).getControllerManager();
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
