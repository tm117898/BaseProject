// AidlInterface.aidl
package cesc.shang.demo.examples.ipc.aidl;
import cesc.shang.demo.examples.ipc.aidl.AIDLEntity;
import cesc.shang.demo.examples.ipc.aidl.AIDLCallBack;
// Declare any non-default types here with import statements

interface AidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    void sendEntity(in AIDLEntity entity);

    void registerCallBack(in AIDLCallBack callBack);
    void unRegisterCallBack(in AIDLCallBack callBack);
}
