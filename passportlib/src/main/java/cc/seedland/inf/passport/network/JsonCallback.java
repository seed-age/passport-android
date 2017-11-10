package cc.seedland.inf.passport.network;

import com.lzy.okgo.callback.AbsCallback;

/**
 * Created by xuchunlei on 2017/11/10.
 */

public abstract class JsonCallback<T> extends AbsCallback<T> {

    @Override
    public T convertResponse(okhttp3.Response response) throws Throwable {
        return null;
    }
}
