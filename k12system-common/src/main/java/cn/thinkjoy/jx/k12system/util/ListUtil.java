package cn.thinkjoy.jx.k12system.util;

/**
 * Created by yhwang on 15/9/1.
 */
public class ListUtil {
    public static boolean isAllNull(Iterable<?> list) {
        for (Object obj : list) {
            if (obj != null)
                return false;
        }

        return true;
    }
}
