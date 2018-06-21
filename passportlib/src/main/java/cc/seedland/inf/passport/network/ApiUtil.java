package cc.seedland.inf.passport.network;

import android.text.TextUtils;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.regex.Pattern;

import cc.seedland.inf.network.Networkit;
import cc.seedland.inf.network.SeedCallback;
import cc.seedland.inf.passport.BuildConfig;
import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.common.TokenBean;
import cc.seedland.inf.passport.util.Constant;
import cc.seedland.inf.passport.util.ValidateUtil;

/**
 * 构造API请求时使用
 * Created by xuchunlei on 2017/11/13.
 */

public class ApiUtil {

    // 正则表达式 - host
//    private static final Pattern HOST_REGEX = Pattern.compile("^(http|https):\\/\\/(([a-zA-Z0-9]*\\-?[a-zA-Z0-9]*)\\.)*([A-Za-z]*)$");


    private static boolean refreshing;

    private ApiUtil(){

    }


    // 刷新Token
    public static void refreshToken() {
        refreshToken(null);

    }

    public static void refreshToken(final TokenCallback callback) {
        final String cachedToken = RuntimeCache.getToken();
        if(!ValidateUtil.checkNull(cachedToken)) { // 缓存过Token，则进行刷新
            refreshing = true;
            OkGo.<TokenBean>post(Networkit.generateFullUrl(Constant.API_URL_TOKEN))
                    .params("sso_tk", RuntimeCache.getToken())
                    .execute(new SeedCallback<TokenBean>(TokenBean.class) {
                        @Override
                        public void onSuccess(Response<TokenBean> response) {
                            RuntimeCache.saveToken(response.body().token);

                            if(BuildConfig.API_ENV) {
                                Toast.makeText(Constant.APP, RuntimeCache.getToken(), Toast.LENGTH_LONG).show();
                            }
                            if(callback != null) {
                                callback.onTokenReceived(RuntimeCache.getToken());
                            }

                        }

                        @Override
                        public void onError(Response<TokenBean> response) {
                            super.onError(response);
                            String msg = response.getException().getMessage();
                            if(!TextUtils.isEmpty(msg)) {
                                Toast.makeText(Constant.APP, msg, Toast.LENGTH_LONG).show();
                            }
                            if(!Constant.getString(R.string.error_network).equals(msg)) { // 不是因为没有网络，导致的刷新缓存错误
                                RuntimeCache.saveToken("");
                            }
                            if(callback != null) {
                                callback.onTokenExpired(cachedToken);
                            }

                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                            refreshing = false;
                        }
                    });
        }
    }

    public static boolean isRefreshing() {
        return refreshing;
    }

}
