// BindTestFirst.aidl
package cesc.shang.demo.examples.ipc.binderconnectionpool;

// Declare any non-default types here with import statements

interface BinderTestFirst {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    String testFirst(String s);
}
