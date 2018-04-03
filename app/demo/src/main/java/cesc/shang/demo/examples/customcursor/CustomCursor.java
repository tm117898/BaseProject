package cesc.shang.demo.examples.customcursor;

import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.util.ArrayMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cesc.shang.baselib.support.context.IContextSupport;
import cesc.shang.utilslib.utils.debug.LogUtils;

/**
 * Created by Cesc Shang on 2017/12/19.
 */

public class CustomCursor implements Cursor {
    private IContextSupport mContext;
    private LogUtils mLog;

    private static final String[] COLUMN_NAMES = {"name", "age", "address"};
    private static final int ROWS_COUNT = 5;
    private static final int COLUMN_COUNT = COLUMN_NAMES.length;
    private List<Map<String, String>> mData;

    private int mPosition = 0;

    public CustomCursor(IContextSupport context) {
        mContext = context;
        mLog = context.getUtilsManager().getLogUtils(this.getClass().getSimpleName());
        mLog.i("create()");

        mData = new ArrayList<>(ROWS_COUNT);
        for (int i = 0; i < ROWS_COUNT; i++) {
            Map<String, String> row = new ArrayMap<>(COLUMN_COUNT);
            for (int j = 0; j < COLUMN_COUNT; j++) {
                String columnName = COLUMN_NAMES[j];
                row.put(columnName, columnName + i);
            }
            mData.add(row);
        }
    }

    @Override
    public void close() {
        mLog.i("close()");
        mContext = null;

        for (int i = 0; i < ROWS_COUNT; i++) {
            mData.get(i).clear();
        }
        mData.clear();
    }

    @Override
    public boolean isClosed() {
        mLog.i("isClosed()");
        return mContext == null;
    }

    @Override
    public int getCount() {
        mLog.i("getCount()");
        return ROWS_COUNT;
    }

    @Override
    public int getPosition() {
        mLog.i("getPosition()");
        return mPosition;
    }

    @Override
    public boolean move(int offset) {
        mLog.i("move() , offset : ", offset);
        int tagPosition = mPosition + offset;
        return moveToPosition(tagPosition);
    }

    @Override
    public boolean moveToPosition(int position) {
        mLog.i("moveToPosition() , position : ", position);
        if (0 <= position && position < ROWS_COUNT) {
            mPosition = position;
            return true;
        }
        return false;
    }

    @Override
    public boolean moveToFirst() {
        mLog.i("moveToFirst()");
        mPosition = 0;
        return true;
    }

    @Override
    public boolean moveToLast() {
        mLog.i("moveToLast()");
        mPosition = ROWS_COUNT - 1;
        return true;
    }

    @Override
    public boolean moveToNext() {
        mLog.i("moveToNext()");
        return move(1);
    }

    @Override
    public boolean moveToPrevious() {
        mLog.i("moveToPrevious()");
        return move(-1);
    }

    @Override
    public boolean isFirst() {
        mLog.i("isFirst()");
        return mPosition == 0;
    }

    @Override
    public boolean isLast() {
        mLog.i("isLast()");
        return mPosition == ROWS_COUNT - 1;
    }

    @Override
    public boolean isBeforeFirst() {
        mLog.i("isBeforeFirst()");
        return false;
    }

    @Override
    public boolean isAfterLast() {
        mLog.i("isAfterLast()");
        return mPosition == ROWS_COUNT;
    }

    @Override
    public int getColumnIndex(String columnName) {
        mLog.i("getColumnIndex() , columnName : " + columnName);
        for (int i = 0; i < COLUMN_COUNT; i++) {
            if (COLUMN_NAMES[i].equals(columnName)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getColumnIndexOrThrow(String columnName) throws IllegalArgumentException {
        mLog.i("getColumnIndexOrThrow() , columnName : " + columnName);
        int index = getColumnIndex(columnName);
        if (index < 0) {
            throw new IllegalArgumentException("no such column");
        }
        return 0;
    }

    @Override
    public String getColumnName(int columnIndex) {
        mLog.i("getColumnName() , columnIndex : " + columnIndex);
        if (0 <= columnIndex && columnIndex < COLUMN_COUNT) {
            return COLUMN_NAMES[columnIndex];
        }
        return null;
    }

    @Override
    public String[] getColumnNames() {
        mLog.i("getColumnNames()");
        return COLUMN_NAMES;
    }

    @Override
    public int getColumnCount() {
        mLog.i("getColumnCount()");
        return COLUMN_COUNT;
    }

    @Override
    public byte[] getBlob(int columnIndex) {
        mLog.i("getBlob() , columnIndex : ", columnIndex);
        return new byte[0];
    }

    @Override
    public String getString(int columnIndex) {
        mLog.i("getString() , columnIndex : ", columnIndex);
        Map<String, String> row = mData.get(mPosition);
        return row.get(getColumnName(columnIndex));
    }

    @Override
    public void copyStringToBuffer(int columnIndex, CharArrayBuffer buffer) {
        mLog.i("copyStringToBuffer() , columnIndex : ", columnIndex, " , buffer : " + buffer);
    }

    @Override
    public short getShort(int columnIndex) {
        mLog.i("getShort() , columnIndex : ", columnIndex);
        return 0;
    }

    @Override
    public int getInt(int columnIndex) {
        mLog.i("getInt() , columnIndex : ", columnIndex);
        return 0;
    }

    @Override
    public long getLong(int columnIndex) {
        mLog.i("getLong() , columnIndex : ", columnIndex);
        return 0;
    }

    @Override
    public float getFloat(int columnIndex) {
        mLog.i("getFloat() , columnIndex : ", columnIndex);
        return 0;
    }

    @Override
    public double getDouble(int columnIndex) {
        mLog.i("getDouble() , columnIndex : ", columnIndex);
        return 0;
    }

    @Override
    public int getType(int columnIndex) {
        mLog.i("getType() , columnIndex : ", columnIndex);
        return FIELD_TYPE_STRING;
    }

    @Override
    public boolean isNull(int columnIndex) {
        mLog.i("isNull() , columnIndex : ", columnIndex);
        Map<String, String> row = mData.get(mPosition);
        return row.get(getColumnName(columnIndex)) == null;
    }

    @Override
    public void deactivate() {
        mLog.i("deactivate()");
    }

    @Override
    public boolean requery() {
        mLog.i("requery()");
        return false;
    }

    @Override
    public void registerContentObserver(ContentObserver observer) {
        mLog.i("registerContentObserver() , observer : ", observer);
    }

    @Override
    public void unregisterContentObserver(ContentObserver observer) {
        mLog.i("unregisterContentObserver() , observer : ", observer);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        mLog.i("registerDataSetObserver() , observer : ", observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        mLog.i("unregisterDataSetObserver() , observer : ", observer);
    }

    @Override
    public void setNotificationUri(ContentResolver cr, Uri uri) {
        mLog.i("setNotificationUri() , uri : ", uri);
    }

    @Override
    public Uri getNotificationUri() {
        mLog.i("getNotificationUri()");
        return null;
    }

    @Override
    public boolean getWantsAllOnMoveCalls() {
        mLog.i("getWantsAllOnMoveCalls()");
        return false;
    }

    @Override
    public void setExtras(Bundle extras) {
        mLog.i("setExtras()");
    }

    @Override
    public Bundle getExtras() {
        mLog.i("getExtras()");
        return null;
    }

    @Override
    public Bundle respond(Bundle extras) {
        mLog.i("respond()");
        return null;
    }
}
