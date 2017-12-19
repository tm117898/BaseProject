package cesc.shang.demo.examples.customcursor;

import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import cesc.shang.baselib.base.database.BaseContentProvider;

/**
 * Created by Cesc Shang on 2017/12/19.
 */

public class CustomCursorProvider extends BaseContentProvider {
    public static final String AUTHORITY = "cesc.shang.demo.examples.customcursor";
    public static final int URI_MATCH_CODE = 1219;
    private static final UriMatcher URI_MATCHER = new UriMatcher(-1);

    static {
        URI_MATCHER.addURI(AUTHORITY, null, URI_MATCH_CODE);
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        super.query(uri, projection, selection, selectionArgs, sortOrder);
        return new CustomCursor(this);
    }
}
