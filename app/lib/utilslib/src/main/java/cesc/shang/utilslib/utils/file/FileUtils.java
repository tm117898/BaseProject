
package cesc.shang.utilslib.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.storage.StorageManager;

public class FileUtils {

    public FileUtils() {
    }

    public boolean exitSDcard() {
        return isExternalStorageMounted();
    }

    /**
     * 获取传入路径的下一级文件夹地址集合
     */
    public List<String> getFilesList(String[] paths) {
        List<String> lstFile = new ArrayList<String>();
        for (String path : paths) {
            // lstFile.add(path);
            File[] files = new File(path).listFiles();
            File f = null;
            if (files != null && files.length > 0) {
                for (int i = 0; i < files.length; i++) {
                    f = files[i];
                    if (f.isDirectory() && f.getPath().indexOf("/.") == -1 && f.canRead())
                        lstFile.add(f.getPath());
                }
            }
        }
        return lstFile;
    }

    @SuppressLint("NewApi")
    public String[] getExternalStorageRootDirectorys(Activity activity) {
        StorageManager storageManager = null;
        Method methodGetPaths = null;
        String[] paths = null;
        if (activity != null) {
            try {
                storageManager = (StorageManager) activity.getSystemService(Activity.STORAGE_SERVICE);
                methodGetPaths = storageManager.getClass().getMethod("getVolumePaths");
                paths = (String[]) methodGetPaths.invoke(storageManager);
            } catch (Exception e) {
                e.printStackTrace();
                paths = new String[]{
                        getExternalStorageDirectory()
                };
            }
        }
        activity = null;
        storageManager = null;
        methodGetPaths = null;
        return paths;
    }

    /**
     * 判断文件或者文件夹是否存在
     *
     * @param path 文件或者文件夹路径
     */
    public boolean fileOrFolderIsExist(String path) {
        File file = new File(path);
        if (file.exists()) {
            file = null;
            return true;
        }
        file = null;
        return false;
    }

    public boolean fileOrFolderIsExist(File file) {
        if (file.exists()) {
            file = null;
            return true;
        }
        file = null;
        return false;
    }

    public void createFolder(String path) {
        File file = new File(path);
        if (!fileOrFolderIsExist(file))
            file.mkdir();
        file = null;
    }

    public void deleteFileOrFolder(File file) {
        if (fileOrFolderIsExist(file))
            file.delete();
        file = null;
    }

    public void deleteFileOrFolder(String path) {
        deleteFileOrFolder(new File(path));
    }

    public void copyFile(String fromFilePath, String toFilePath, Boolean rewrite) {
        File fromFile = new File(fromFilePath);
        File toFile = new File(toFilePath);
        copyFile(fromFile, toFile, rewrite);
        fromFile = null;
        toFile = null;
    }

    public void copyFile(File fromFile, File toFile, Boolean rewrite) {
        if (!fromFile.exists()) {
            return;
        }
        if (!fromFile.isFile()) {
            return;
        }
        if (!fromFile.canRead()) {
            return;
        }
        if (!toFile.getParentFile().exists()) {
            return;
        }
        if (toFile.exists() && rewrite) {
            toFile.delete();
        }
        FileInputStream fosfrom = null;
        FileOutputStream fosto = null;
        try {
            fosfrom = new FileInputStream(fromFile);
            fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fromFile = null;
            toFile = null;
            try {
                if (fosfrom != null) {
                    fosfrom.close();
                }
                if (fosto != null) {
                    fosto.flush();
                    fosto.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean saveBitmap(String folderPath, String fileName, Bitmap bitmap) {
        boolean b = false;
        createFolder(folderPath);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(folderPath + "/" + fileName);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);// 把数据写入文件
            fos.flush();
            b = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bitmap = null;
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                b = false;
            }
        }
        return b;
    }

    public boolean isExternalStorageMounted() {
        File extDir = new File(getExternalStorageDirectory());
        boolean isExist = extDir.exists();
        extDir = null;
        return isExist;
    }

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

    public File getExternalStoragePublicDirectory(String name) {
        File dirFile = new File(getExternalStorageDirectory());
        return new File(dirFile, name);
    }

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

    public boolean saveFileByInputStream(InputStream is, String filePath, String fileName, Boolean rewrite) {
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

    public boolean saveFileByInputStream(InputStream is, File toFile) {
        boolean b = false;

        if (toFile.exists()) {
            toFile.delete();
        }
        FileOutputStream fosto = null;
        try {
            fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = is.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }

            b = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            toFile = null;
            if (is != null) {
                is.close();
                is = null;
            }
            if (fosto != null) {
                fosto.flush();
                fosto.close();
                fosto = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            is = null;
            fosto = null;
        }

        return b;
    }

    public void scanFileToSystem(Context context, String fileAbsPath) {
        Uri uri = Uri.parse("file://" + fileAbsPath);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
        context.sendBroadcast(intent);
    }

    public File getSdCardCatchFile(Context context) {
        return context.getExternalCacheDir();
    }

    public String getSdCardCatchPath(Context context) {
        return getSdCardCatchFile(context).getAbsolutePath();
    }
}
