package cesc.shang.utilslib.utils.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by shanghaolongteng on 2016/7/17.
 */
public class SerializableUtils {
    public SerializableUtils() {
    }

    /**
     * 将对象写入到文件
     *
     * @param fileAbsPath 文件绝对路径
     * @param object      要写的对象
     */
    public void writeObject(String fileAbsPath, Serializable object) {
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileAbsPath))) {
            os.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将文件中对象还原到内存
     *
     * @param fileAbsPath 文件绝对路径
     * @return 对象，读取失败返回null
     */
    public Object readObject(String fileAbsPath) {
        Object object = null;
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileAbsPath))) {
            object = is.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            object = null;
        }
        return object;
    }
}
