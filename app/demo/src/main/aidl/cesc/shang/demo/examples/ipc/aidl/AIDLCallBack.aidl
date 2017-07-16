// AIDLCallBack.aidl
package cesc.shang.demo.examples.ipc.aidl;
import cesc.shang.demo.examples.ipc.aidl.AIDLEntity;
// Declare any non-default types here with import statements

interface AIDLCallBack {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    void callBack(in AIDLEntity entity);
}
