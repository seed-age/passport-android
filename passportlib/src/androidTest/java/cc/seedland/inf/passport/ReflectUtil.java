package cc.seedland.inf.passport;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by xuchunlei on 2017/12/20.
 */

public class ReflectUtil {

    /**
     * 通过私有构造方法创建实例
     * @param className
     * @param <T>
     * @return
     */
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

    /**
     * 获取私有静态字段的值
     * @param instance
     * @param name
     * @return
     */
    public static <T> T getValue(Object instance, String name) {

        try {
            Field field;
            field = instance.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return (T)field.get(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * 设置静态私有变量的值
     * @param instance
     * @param name
     * @param value
     */
    public static void setValue(Object instance, String name, Object value) {

        try {
            Field field;
            field = instance.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(instance, value);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 设置静态私有变量的值
     * @param clazz
     * @param name
     * @param value
     */
    public static void setValue(Class clazz, String name, Object value) {
        try {
            Field field;
            field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            field.set(clazz, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 调用无参数的私有静态方法
     * @param className
     * @param methodName
     * @param <T>
     * @return
     */
    public static <T> T callPrivateMethod(String className, String methodName) {
        try {
            Class clz = Class.forName(className);
            Method method = clz.getDeclaredMethod(methodName);
            method.setAccessible(true);
            return (T) method.invoke(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 调用带有参数的私有静态方法
     * @param className
     * @param methodName
     * @param paramsTypes
     * @param params
     * @param <T>
     * @return
     */
    public static <T> T callPrivateMethod(String className, String methodName, Class<?>[] paramsTypes, Object... params) {
        try {
            Class clz = Class.forName(className);
            Method method = clz.getDeclaredMethod(methodName, paramsTypes);
            method.setAccessible(true);
            return (T) method.invoke(null, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
