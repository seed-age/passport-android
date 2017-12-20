package cc.seedland.inf.passport.network;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.util.Constant;
import cc.seedland.inf.passport.util.DeviceUtil;
import cc.seedland.inf.passport.util.GsonHolder;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by xuchunlei on 2017/11/16.
 */

public class PassportInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if(!DeviceUtil.isNetworkConnected()) {
            throw new IOException(Constant.getString(R.string.error_network));
        }else {
            Response response = chain.proceed(request);
            String raw = response.body().string();
            BeanWrapper wrapper = GsonHolder.getInstance().fromJson(raw, BeanWrapper.class);
            if(wrapper.checkSign() && wrapper.code == Constant.RESPONSE_CODE_SUCCESS) {
                MediaType contentType = response.body().contentType();
                JsonElement bodyObj = wrapper.data == null ? new JsonObject() : wrapper.data;
                bodyObj.getAsJsonObject().addProperty("raw", raw);
                ResponseBody body = ResponseBody.create(contentType, GsonHolder.getInstance().toJson(bodyObj));
                return response.newBuilder()
                        .code(response.code())
                        .body(body)
                        .build();
            }else {
                throw new IOException(wrapper.message);
            }
        }
    }
}
