package cesc.shang.utilslib.utils.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by shanghaolongteng on 2016/8/2.
 */
public class MapSetUtils {
    public interface MapTraversalCallBack<K, V> {
        boolean findObject(K key, V value);
    }

    public static <K, V> void traversalMap(Map<K, V> map, MapTraversalCallBack<K, V> callBack) {
        if (map != null && map.size() > 0) {
            Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<K, V> entry = it.next();
                boolean find = callBack.findObject(entry.getKey(), entry.getValue());

                if (find) {
                    break;
                }
            }
        }
    }

    public interface SetTraversalCallBack<V> {
        boolean findObject(V value);
    }

    public static <V> void traversalSet(Set<V> set, SetTraversalCallBack<V> callBack) {
        if (set != null && set.size() > 0) {
            Iterator<V> it = set.iterator();
            while (it.hasNext()) {
                V entry = it.next();
                boolean find = callBack.findObject(entry);

                if (find) {
                    break;
                }
            }
        }
    }

}
