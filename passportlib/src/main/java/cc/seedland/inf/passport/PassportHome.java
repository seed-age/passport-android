package cc.seedland.inf.passport;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import cc.seedland.inf.passport.login.LoginCaptchaActivity;
import cc.seedland.inf.passport.login.LoginPasswordActivity;
import cc.seedland.inf.passport.network.ApiUtil;
import cc.seedland.inf.passport.password.modify.ModifyPasswordActivity;
import cc.seedland.inf.passport.password.reset.ResetPasswordActivity;
import cc.seedland.inf.passport.register.RegisterActivity;
import cc.seedland.inf.passport.util.Constant;
import okhttp3.OkHttpClient;

/**
 * Passport门面,这里统一提供对外调用接口
 * Created by xuchunlei on 2017/11/10.
 */

public final class PassportHome {

    private static final PassportHome INSTANCE = new PassportHome();
    private PassportHome(){

    }

    /**
     * 初始化方法
     * <p>
     *     初始化PassportSDK开发环境
     * </p>
     * @param app Application实例
     * @param channel 渠道号
     * @param key 开发者key
     */
    public void init(Application app, String channel, String key) {

        Constant.APP_CONTEXT = app.getApplicationContext();

        // 初始化OkGo
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if(BuildConfig.DEBUG) {
            // 日志支持
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("seeldand-passport");
            loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
            loggingInterceptor.setColorLevel(Level.INFO);
            builder.addInterceptor(loggingInterceptor);
        }
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)      // 全局读取超时时间
               .writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)     // 全局写入超时时间
               .connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);  // 全局连接超时时间

        HttpHeaders headers = new HttpHeaders();
        // TODO: 2017/11/13 增加若干参数到header 
        
        OkGo.getInstance().init(app)
                          .setOkHttpClient(builder.build())
                          .setCacheMode(CacheMode.NO_CACHE)
                          .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
                          .setRetryCount(3)
                          .addCommonHeaders(headers);

        // 初始化ApiUtil
        ApiUtil.init(channel, key, app.getString(R.string.sit_http_host));

        app.registerActivityLifecycleCallbacks(new PassportLifecycleCallbacks());
    }

    public final static PassportHome getInstance() {
        return INSTANCE;
    }

    /**
     * 打开注册界面
     * @param context
     */
    public void startRegister(Context context) {
        Intent i = new Intent(context, RegisterActivity.class);
        context.startActivity(i);
    }

    /**
     * 打开密码登录界面
     * @param context
     */
    public void startLoginByPassword(Context context) {
        Intent i = new Intent(context, LoginPasswordActivity.class);
        context.startActivity(i);
    }

    /**
     * 打开验证码登录界面
     * @param context
     */
    public void startLoginByCaptcha(Context context) {
        Intent i = new Intent(context, LoginCaptchaActivity.class);
        context.startActivity(i);
    }

    /**
     * 打开重置密码界面
     * @param context
     */
    public void startResetPassword(Context context) {
        Intent i = new Intent(context, ResetPasswordActivity.class);
        context.startActivity(i);
    }

    /**
     * 打开修改密码界面
     * @param context
     */
    public void startModifyPassword(Context context) {
        Intent i = new Intent(context, ModifyPasswordActivity.class);
        context.startActivity(i);
    }
}
