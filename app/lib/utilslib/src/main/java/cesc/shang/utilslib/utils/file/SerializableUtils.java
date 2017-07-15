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

    public void writeObject(String fileAbsPath, Serializable object) {
        ObjectOutputStream stream = null;
        try {
            stream = new ObjectOutputStream(new FileOutputStream(fileAbsPath));
            stream.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null)
                    stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Object readObject(String fileAbsPath) {
        Object object = null;
        ObjectInputStream stream = null;
        try {
            stream = new ObjectInputStream(new FileInputStream(fileAbsPath));
            object = stream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null)
                    stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return object;
    }
}
