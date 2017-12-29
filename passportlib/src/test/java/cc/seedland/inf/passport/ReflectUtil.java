package cc.seedland.inf.passport;

import java.lang.reflect.Constructor;

/**
 * Created by xuchunlei on 2017/12/20.
 */

public class ReflectUtil {

    public static <T> T createFromPrivateConstructor(String className){
        try {
            /*可以使用相对路径，同一个包中可以不用带包路径*/
            Class clz = Class.forName(className);
            /*以下调用无参的、私有构造函数*/

            Constructor constructor = clz.getDeclaredConstructor();
            constructor.setAccessible(true);

            T instance = (T)constructor.newInstance();
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
