package cc.seedland.inf.passport.base;

import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Field;

import cc.seedland.inf.passport.util.GsonHolder;

/**
 * Created by xuchunlei on 2017/11/15.
 */

public class BaseBean {


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
                        args.putString(field.getName(), "" + field.get(this));
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
        return GsonHolder.getInstance().toJson(this);
    }
}
