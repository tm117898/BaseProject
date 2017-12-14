package cesc.shang.utilslib.utils.file;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.storage.StorageManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shanghaolongteng on 2016/7/17.
 */
public class FileUtils {
    public static final int INVALID_RESULT_INT = -1;
    public static final int FILE_COPY_BY_STREAM_BUFFER_SIZE = 1024;
    public static final int SAVE_ITMAP_QALITY = 100;

    public FileUtils() {
    }

    /**
     * SD卡是否存在
     *
     * @return true存在，false不存在
     */
    public boolean exitSDcard() {
        return isExternalStorageMounted();
    }

    /**
     * 获取传入路径的下一级文件夹地址集合
     *
     * @param paths 路径集合
     * @return 下一级文件夹地址集合
     */
    public List<String> getFilesList(String[] paths) {
        List<String> lstFile = new ArrayList<>();
        for (String path : paths) {
            // lstFile.add(path);
            File[] files = new File(path).listFiles();
            File f = null;
            if (files != null && files.length > 0) {
                for (int i = 0; i < files.length; i++) {
                    f = files[i];
                    if (f.isDirectory() && f.getPath().indexOf("/.") == INVALID_RESULT_INT && f.canRead()) {
                        lstFile.add(f.getPath());
                    }
                }
            }
        }
        return lstFile;
    }

    /**
     * 获取外部存储个目录集合
     *
     * @param context 上下文
     * @return 目录集合，获取失败返回null
     */
    public String[] getExternalStorageRootDirectorys(Context context) {
        StorageManager storageManager = null;
        Method methodGetPaths = null;
        String[] paths = null;
        if (context != null) {
            try {
                storageManager = (StorageManager) context.getSystemService(Activity.STORAGE_SERVICE);
                methodGetPaths = storageManager.getClass().getMethod("getVolumePaths");
                paths = (String[]) methodGetPaths.invoke(storageManager);
            } catch (Exception e) {
                e.printStackTrace();
                paths = new String[]{
                        getExternalStorageDirectory()
                };
            }
        }
        context = null;
        storageManager = null;
        methodGetPaths = null;
        return paths;
    }

    /**
     * 判断文件或者文件夹是否存在
     *
     * @param path 文件或者文件夹路径
     * @return true存在，false不存在
     */
    public boolean fileOrFolderIsExist(String path) {
        return fileOrFolderIsExist(new File(path));
    }

    /**
     * 判断文件或者文件夹是否存在
     *
     * @param file 文件或者文件夹
     * @return true存在，false不存在
     */
    public boolean fileOrFolderIsExist(File file) {
        if (file.exists()) {
            file = null;
            return true;
        }
        file = null;
        return false;
    }

    /**
     * 创建文件夹
     *
     * @param path 文件夹路径
     */
    public void createFolder(String path) {
        File file = new File(path);
        if (!fileOrFolderIsExist(file)) {
            file.mkdir();
        }
        file = null;
    }

    /**
     * 删除文件夹
     *
     * @param file 文件夹
     */
    public void deleteFileOrFolder(File file) {
        if (fileOrFolderIsExist(file)) {
            file.delete();
        }
        file = null;
    }

    /**
     * 删除文件或者文件夹
     *
     * @param path 文件或者文件夹路径
     */
    public void deleteFileOrFolder(String path) {
        deleteFileOrFolder(new File(path));
    }

    /**
     * 通过流复制文件
     *
     * @param fromFilePath 源文件绝对路径
     * @param toFilePath   目标文件绝对路径
     * @param reWrite      目标文件存在是否允许重写
     */
    public void copyFile(String fromFilePath, String toFilePath, boolean reWrite) {
        File fromFile = new File(fromFilePath);
        File toFile = new File(toFilePath);
        copyFile(fromFile, toFile, reWrite);
    }

    /**
     * 通过流复制文件
     *
     * @param fromFile 源文件
     * @param toFile   目标文件
     * @param reWrite  目标文件存在是否允许重写
     */
    public void copyFile(File fromFile, File toFile, boolean reWrite) {
        if (fromFile.exists() && fromFile.isFile() && fromFile.canRead() && toFile.getParentFile().exists()) {
            if (toFile.exists() && reWrite) {
                toFile.delete();
            }

            try (FileInputStream is = new FileInputStream(fromFile);
                 FileOutputStream os = new FileOutputStream(toFile)) {
                byte[] bt = new byte[FILE_COPY_BY_STREAM_BUFFER_SIZE];
                int c;
                while ((c = is.read(bt)) > 0) {
                    os.write(bt, 0, c);
                }
                os.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 保存bitmap到文件
     *
     * @param folderPath 文件夹绝对路径
     * @param fileName   文件名称
     * @param bitmap     要保存的bitmap
     * @return true成功，false失败
     */
    public boolean saveBitmap(String folderPath, String fileName, Bitmap bitmap) {
        boolean b = false;
        createFolder(folderPath);

        try (FileOutputStream fos = new FileOutputStream(folderPath + "/" + fileName)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, SAVE_ITMAP_QALITY, fos);
            fos.flush();
            b = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * 外部存储是否安装
     *
     * @return true在，false不在
     */
    public boolean isExternalStorageMounted() {
        File extDir = new File(getExternalStorageDirectory());
        boolean isExist = extDir.exists();
        extDir = null;
        return isExist;
    }

    /**
     * 获取外部存储路径
     *
     * @return 绝对路径
     */
    public String getExternalStorageDirectory() {
        try {
            Class clazz = Class.forName("android.os.Environment");
            Method method = clazz.getMethod("getPrimaryStorageDirectory", new Class[0]);
            File primaryStorage = (File) method.invoke(null, new Object[0]);
            if (primaryStorage != null && primaryStorage.exists()) {
                return primaryStorage.getAbsolutePath();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Class clazz = Class.forName("android.os.Environment");
            Method method = clazz.getMethod("getSecondStorageDirectory", new Class[]{});
            File secondStorage = (File) method.invoke(null, new Object[0]);
            if (secondStorage != null && secondStorage.exists()) {
                return secondStorage.getAbsolutePath();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 获取Assets目录中文件is
     *
     * @param context  上下文
     * @param filePath 文件路径
     * @return InputStream，失败返回null
     */
    public InputStream getAssetsFileInputStream(Context context, String filePath) {
        InputStream is = null;
        try {
            is = context.getAssets().open(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        context = null;
        return is;
    }

    /**
     * 通过InputStream将文件保存到指定路径
     *
     * @param is       要保存的is
     * @param filePath 文件夹路径
     * @param fileName 文件名称
     * @param rewrite  文件存在是否允许重写
     * @return true成功，false失败
     */
    public boolean saveFileByInputStream(InputStream is, String filePath, String fileName, boolean rewrite) {
        boolean b = false;
        createFolder(filePath);
        File toFile = new File(filePath + "/" + fileName);

        if (rewrite) {
            b = saveFileByInputStream(is, toFile);
        } else {
            if (toFile.exists() && toFile.length() > 0) {
                if (is != null) {
                    try {
                        is.close();
                        is = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                        is = null;
                    }
                }
                b = true;
            } else {
                b = saveFileByInputStream(is, toFile);
            }
        }
        return b;
    }

    /**
     * 通过InputStream将文件保存到指定路径
     *
     * @param is     要保存的is
     * @param toFile 要保存文件
     * @return true成功，false失败
     */
    public boolean saveFileByInputStream(InputStream is, File toFile) {
        boolean b = false;
        if (toFile.exists()) {
            toFile.delete();
        }

        try (FileOutputStream os = new FileOutputStream(toFile)) {
            byte[] bt = new byte[FILE_COPY_BY_STREAM_BUFFER_SIZE];
            int c;
            while ((c = is.read(bt)) > 0) {
                os.write(bt, 0, c);
            }
            os.flush();
            b = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return b;
    }

    /**
     * 将新增文件扫描到系统媒体库
     *
     * @param context     上下文
     * @param fileAbsPath 新增文件绝对路径
     */
    public void scanFileToSystem(Context context, String fileAbsPath) {
        Uri uri = Uri.parse("file://" + fileAbsPath);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
        context.sendBroadcast(intent);
    }

    /**
     * 获取sd卡下缓存文件夹
     *
     * @param context 上下文
     * @return file
     */
    public File getSdCardCatchFile(Context context) {
        return context.getExternalCacheDir();
    }

    /**
     * 获取sd卡下缓存文件夹路径
     *
     * @param context 上下文
     * @return file路径
     */
    public String getSdCardCatchPath(Context context) {
        return getSdCardCatchFile(context).getAbsolutePath();
    }
}
