package cesc.shang.utilslib.utils.widget;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

/**
 * Created by shanghaolongteng on 2016/7/15.
 */
public class BitmapUtils {
    public static final int SAMPLE_SIZE_FACTOR = 2;

    public BitmapUtils() {
    }

    /**
     * 根据View大小将Drawable转换成Bitmap
     *
     * @param view     决定Bitmap大小的View
     * @param bitmapId Drawable id
     * @return Bitmap
     */
    public Bitmap decodeBitmapByView(View view, int bitmapId) {
        BitmapFactory.Options options = getMeasureOptions();
        Resources res = view.getResources();
        BitmapFactory.decodeResource(res, bitmapId, options);
        getDecodeOptions(view, options);
        return BitmapFactory.decodeResource(res, bitmapId, options);
    }

    /**
     * 设置测量options参数
     *
     * @param view    决定Bitmap大小的View
     * @param options 测量参数
     */
    private void getDecodeOptions(View view, BitmapFactory.Options options) {
        options.inJustDecodeBounds = false;
        options.inSampleSize = calculateInSampleSize(view, options);
    }

    /**
     * 获取缩放options参数
     *
     * @return Options
     */
    private BitmapFactory.Options getMeasureOptions() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        return options;
    }

    /**
     * 计算缩放比
     *
     * @param view    决定Bitmap大小的View
     * @param options 测量参数
     * @return 缩放比，2的倍数
     */
    public int calculateInSampleSize(View view, BitmapFactory.Options options) {
        int viewWidth = view.getMeasuredWidth();
        int viewHeight = view.getMeasuredHeight();

        return calculateInSampleSize(options, viewWidth, viewHeight);
    }

    /**
     * 计算缩放比
     *
     * @param options    测量参数
     * @param viewWidth  要生成Bitmap的宽
     * @param viewHeight 要生成Bitmap的高
     * @return 缩放比，2的倍数
     */
    public int calculateInSampleSize(BitmapFactory.Options options, int viewWidth, int viewHeight) {
        int inSampleSize = 1;
        int bitmapWidth = options.outWidth;
        int bitmapHeight = options.outHeight;

        if (bitmapWidth > viewWidth || bitmapHeight > viewHeight) {
            int halfBitmapWidth = bitmapWidth / SAMPLE_SIZE_FACTOR;
            int halfBitmapHeight = bitmapHeight / SAMPLE_SIZE_FACTOR;

            while ((halfBitmapWidth / inSampleSize) >= viewWidth
                    && (halfBitmapHeight / inSampleSize) >= viewHeight) {
                inSampleSize *= SAMPLE_SIZE_FACTOR;
            }
        }

        return inSampleSize;
    }
}
