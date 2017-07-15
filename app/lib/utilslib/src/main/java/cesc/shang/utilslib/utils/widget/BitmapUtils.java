package cesc.shang.utilslib.utils.widget;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

/**
 * Created by shanghaolongteng on 2016/7/15.
 */
public class BitmapUtils {
    public BitmapUtils() {
    }

    public Bitmap decodeBitmapByView(View view, int bitmapId) {
        BitmapFactory.Options options = getMeasureOptions();
        Resources res = view.getResources();
        BitmapFactory.decodeResource(res, bitmapId, options);
        getDecodeOptions(view, options);
        return BitmapFactory.decodeResource(res, bitmapId, options);
    }

    private void getDecodeOptions(View view, BitmapFactory.Options options) {
        options.inJustDecodeBounds = false;
        options.inSampleSize = calculateInSampleSize(view, options);
    }

    private BitmapFactory.Options getMeasureOptions() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        return options;
    }

    public int calculateInSampleSize(View view, BitmapFactory.Options options) {
        int viewWidth = view.getMeasuredWidth();
        int viewHeight = view.getMeasuredHeight();

        return calculateInSampleSize(options, viewWidth, viewHeight);
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int viewWidth, int viewHeight) {
        int inSampleSize = 1;
        int bitmapWidth = options.outWidth;
        int bitmapHeight = options.outHeight;

        if (bitmapWidth > viewWidth || bitmapHeight > viewHeight) {
            int halfBitmapWidth = bitmapWidth / 2;
            int halfBitmapHeight = bitmapHeight / 2;

            while (
                    (halfBitmapWidth / inSampleSize) >= viewWidth &&
                            (halfBitmapHeight / inSampleSize) >= viewHeight) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
