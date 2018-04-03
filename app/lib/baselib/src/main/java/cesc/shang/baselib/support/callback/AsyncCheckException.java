package cesc.shang.baselib.support.callback;

/**
 * Created by Cesc Shang on 2018/03/23.
 */

public class AsyncCheckException extends NullPointerException {

    public AsyncCheckException(String message) {
        super(message);
    }

    public interface Checkable {
        /**
         * 检查引用是否为空，为空抛出异异常
         *
         * @throws AsyncCheckException
         */
        void checkWeakReferenceInstance() throws AsyncCheckException;
    }
}
