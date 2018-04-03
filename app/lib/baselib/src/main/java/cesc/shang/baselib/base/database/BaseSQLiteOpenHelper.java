package cesc.shang.baselib.base.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.Arrays;
import java.util.List;

import cesc.shang.baselib.base.application.BaseApplication;
import cesc.shang.baselib.support.context.INotContextSupport;
import cesc.shang.baselib.support.manager.ControllerManager;
import cesc.shang.baselib.support.manager.HandlerManager;
import cesc.shang.baselib.support.manager.UtilsManager;
import cesc.shang.utilslib.utils.debug.LogUtils;

/**
 * Created by shanghaolongteng on 2016/7/31.
 */
public class BaseSQLiteOpenHelper extends SQLiteOpenHelper implements INotContextSupport {
    public static final int ROWS_AFFECTED_COUNT = 0;

    protected LogUtils mLog;

    public BaseSQLiteOpenHelper(Context context, String name, int version) {
        this(context, name, null, version, null);
    }

    public BaseSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        this(context, name, factory, version, null);
    }

    public BaseSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version,
                                DatabaseErrorHandler errorHandler) {
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

    /**
     * 插入数据
     *
     * @param sql      sql语句
     * @param callBack 绑定数据callBack
     * @param ts       要插入数据集合
     * @param <T>      要插入数据类型
     * @return 受影响行数，操作失败返回{@link #ROWS_AFFECTED_COUNT}
     */
    public <T> long insert(String sql, BindStatementDataCallBack<T> callBack, T... ts) {
        return insert(sql, callBack, Arrays.asList(ts));
    }

    /**
     * 插入数据
     *
     * @param sql      sql语句
     * @param callBack 绑定数据callBack
     * @param ts       要插入数据集合
     * @param <T>      要插入数据类型
     * @return 受影响行数，操作失败返回{@link #ROWS_AFFECTED_COUNT}
     */
    public <T> long insert(String sql, BindStatementDataCallBack<T> callBack, List<T> ts) {
        return operation(sql, ts, callBack, new StatementOperation() {
            @Override
            public long operation(SQLiteStatement statement) {
                return statement.executeInsert();
            }
        });
    }

    /**
     * 更新或者删除数据
     *
     * @param sql      sql语句
     * @param callBack 绑定数据callBack
     * @param ts       要更新或者删除数据集合
     * @param <T>      要更新或者删除数据类型
     * @return 受影响行数，操作失败返回{@link #ROWS_AFFECTED_COUNT}
     */
    public <T> long updateOrDelete(String sql, BindStatementDataCallBack<T> callBack, T... ts) {
        return updateOrDelete(sql, callBack, Arrays.asList(ts));
    }

    /**
     * 更新或者删除数据
     *
     * @param sql      sql语句
     * @param callBack 绑定数据callBack
     * @param ts       要更新或者删除数据集合
     * @param <T>      要更新或者删除数据类型
     * @return 受影响行数，操作失败返回{@link #ROWS_AFFECTED_COUNT}
     */
    public <T> long updateOrDelete(String sql, BindStatementDataCallBack<T> callBack, List<T> ts) {
        return operation(sql, ts, callBack, new StatementOperation() {
            @Override
            public long operation(SQLiteStatement statement) {
                return statement.executeUpdateDelete();
            }
        });
    }

    /**
     * 绑定数据到statement，然后执行operation
     *
     * @param sql       sql语句
     * @param ts        要操作数据集合
     * @param cb        绑定数据callBack
     * @param operation 要执行的操作
     * @param <T>       要操作数据类型
     * @return 受影响行数，操作失败返回{@link #ROWS_AFFECTED_COUNT}
     */
    private <T> long operation(String sql, List<T> ts, BindStatementDataCallBack<T> cb, StatementOperation operation) {
        long result = ROWS_AFFECTED_COUNT;

        SQLiteDatabase db = getWritableDatabase();
        if (db.isReadOnly()) {
            mLog.e("operation , db.isReadOnly()!!!");
        } else {
            SQLiteStatement statement = db.compileStatement(sql);
            db.beginTransaction();
            try {
                int size = ts.size();
                int affectedCount = 0;
                for (int i = 0; i < size; i++) {
                    T t = ts.get(i);
                    cb.bind(statement, t);
                    affectedCount += operation.operation(statement);
                    statement.clearBindings();
                }
                db.setTransactionSuccessful();
                result = affectedCount;
            } finally {
                db.endTransaction();
            }
        }

        return result;
    }

    /**
     * 执行数据操作回调
     */
    protected interface StatementOperation {
        /**
         * 要执行的操作,如@{link SQLiteStatement#executeInsert()} or @{link SQLiteStatement#executeUpdateDelete()}
         *
         * @param statement 预处理器
         * @return 插入id或者更新、删除受影响行数
         */
        long operation(SQLiteStatement statement);
    }

    /**
     * 数据绑定回调
     *
     * @param <T> 要操作数据类型
     */
    public interface BindStatementDataCallBack<T> {
        /**
         * 绑定数据
         *
         * @param statement 预处理器
         * @param t         要操作数据
         */
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
