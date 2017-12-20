package cc.seedland.inf.passport.base;

import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import cc.seedland.inf.passport.util.Constant;
import cc.seedland.inf.passport.util.GsonHolder;
import cc.seedland.inf.passport.util.LogUtil;

/**
 * Created by xuchunlei on 2017/11/15.
 */

public class BaseBean {

    public String raw;

    /**
     * 转换成参数
     * @return
     */
    public Bundle toArgs() {

        Bundle args = new Bundle();
        Field[] fields = getClass().getFields();
        for(Field field : fields){
            if(!field.getDeclaringClass().isAssignableFrom(BaseBean.class) && !field.isSynthetic()) { // isSynthetic()方法排除"$change"属性
                try {
                    String key = field.getName();
                    if(!key.equals("serialVersionUID")) {
                        Type type = field.getType();
                        if(type.equals(String.class)) {
                            args.putString(key, field.get(this) + "");
                        } else if(type.equals(Integer.TYPE)) {
                            args.putInt(key, field.getInt(this));
                        } else {
                            args.putString(key, field.get(this) + "");
                        }

                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }

        return args;
    }

    @Override
    public String toString() {
        LogUtil.d(Constant.TAG, "BaseBean.toString:" + raw);
        return raw;
    }
}
