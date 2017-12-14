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
    public static final int BITMAP_FORMAT_SIZE = 1024;

    public LruCatchUtils() {
    }

    /**
     * 获取BitmapLurCatch
     *
     * @param catchSize 缓存最大数量
     * @param <K>       key的类型
     * @return BitmapLurCatch
     */
    public <K> LruCache<K, Bitmap> getBitmapLurCatch(int catchSize) {
        LruCache<K, Bitmap> cache = new LruCache<K, Bitmap>(catchSize) {
            @Override
            protected int sizeOf(K key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / BITMAP_FORMAT_SIZE;
            }
        };
        return cache;
    }

    /**
     * 获取存储LruCache
     *
     * @param directory  缓存绝对路径
     * @param appVersion app版本
     * @param valueCount 缓存最大文件数量
     * @param maxSize    缓存最大大小
     * @return DiskLruCache，获取失败返回null
     */
    public DiskLruCache getDiskLruCache(String directory, int appVersion, int valueCount, long maxSize) {
        return getDiskLruCache(new File(directory), appVersion, valueCount, maxSize);
    }

    /**
     * 获取存储LruCache
     *
     * @param directory  缓存文件
     * @param appVersion app版本
     * @param valueCount 缓存最大文件数量
     * @param maxSize    缓存最大大小
     * @return DiskLruCache，获取失败返回null
     */
    public DiskLruCache getDiskLruCache(File directory, int appVersion, int valueCount, long maxSize) {
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try {
            return DiskLruCache.open(directory, appVersion, valueCount, maxSize);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
