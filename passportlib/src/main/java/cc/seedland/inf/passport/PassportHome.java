package cc.seedland.inf.passport;

import android.app.Application;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.format.Formatter;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import cc.seedland.inf.passport.login.LoginActivity;
import cc.seedland.inf.passport.register.RegisterActivity;
import cc.seedland.inf.passport.util.DeviceUtil;
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
     * @param app
     */
    public void init(Application app, String channel) {

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
        headers.put("channel", channel);
        headers.put("device_type", Build.PRODUCT);
        headers.put("device_mac", DeviceUtil.getMacAddress());
//        headers.put("device_imei", DeviceUtil.getImei(app));
        OkGo.getInstance().init(app)
                          .setOkHttpClient(builder.build())
                          .setCacheMode(CacheMode.NO_CACHE)
                          .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
                          .setRetryCount(3)
                          .addCommonHeaders(headers);
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
     * 打开登录界面
     * @param context
     */
    public void startLogin(Context context) {
        Intent i = new Intent(context, LoginActivity.class);
        context.startActivity(i);
    }

}
