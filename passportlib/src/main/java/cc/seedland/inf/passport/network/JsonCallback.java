package cc.seedland.inf.passport.network;


import android.text.TextUtils;

import com.lzy.okgo.callback.AbsCallback;

import cc.seedland.inf.passport.base.BaseBean;
import cc.seedland.inf.passport.util.Constant;
import cc.seedland.inf.passport.util.GsonHolder;

/**
 * Created by xuchunlei on 2017/11/10.
 */

abstract class JsonCallback<T extends BaseBean> extends AbsCallback<T> {

    private Class<T> clazz;

    public JsonCallback(Class<T> clz) {
        this.clazz = clz;
    }

    @Override
    public T convertResponse(okhttp3.Response response) throws Throwable {

        return GsonHolder.getInstance().fromJson(response.body().string(), clazz);
    }
}
