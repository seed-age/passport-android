package cc.seedland.inf.passport.network;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cc.seedland.inf.passport.util.Constant;
import cc.seedland.inf.passport.util.GsonHolder;
import cc.seedland.inf.passport.util.ValidateUtil;
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
        Response response = chain.proceed(request);

        BeanWrapper wrapper = GsonHolder.getInstance().fromJson(response.body().string(), BeanWrapper.class);
        if(wrapper.checkSign() && wrapper.code == Constant.RESPONSE_CODE_SUCCESS) {
            MediaType contentType = response.body().contentType();
            Object bodyObj = wrapper.data == null ? new JsonObject(): wrapper.data;
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
