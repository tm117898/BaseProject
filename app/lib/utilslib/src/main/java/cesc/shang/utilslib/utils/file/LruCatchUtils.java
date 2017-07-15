package cesc.shang.utilslib.utils.file;

import android.graphics.Bitmap;
import android.util.LruCache;

import java.io.File;
import java.io.IOException;

import cesc.shang.utilslib.libs.DiskLruCache;

/**
 * Created by shanghaolongteng on 2016/8/12.
 */
public class LruCatchUtils {
    public LruCatchUtils() {
    }

    public <K> LruCache<K, Bitmap> getBitmapLurCatch(int catchSize) {
        LruCache<K, Bitmap> cache = new LruCache<K, Bitmap>(catchSize) {
            @Override
            protected int sizeOf(K key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };
        return cache;
    }

    public DiskLruCache getDiskLruCache(String directory, int appVersion, int valueCount, long maxSize) {
        return getDiskLruCache(new File(directory), appVersion, valueCount, maxSize);
    }

    public DiskLruCache getDiskLruCache(File directory, int appVersion, int valueCount, long maxSize) {
        if (!directory.exists())
            directory.mkdirs();

        try {
            return DiskLruCache.open(directory, appVersion, valueCount, maxSize);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
