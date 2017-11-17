package cc.seedland.inf.passport.util;

import android.content.Context;
import android.support.annotation.StringRes;

import cc.seedland.inf.passport.BuildConfig;

/**
 * 常量类,用于定义一些全局使用的常量
 * Created by xuchunlei on 2017/11/10.
 */

public class Constant {

    private Constant() {

    }

    public static final String TAG = "seedland-passport";
    public static final boolean DEBUG = BuildConfig.DEBUG;
    /** 应用的Context */
    public static Context APP_CONTEXT;
    /**用于保存SDK配置的偏好文件名，保存通用配置参数*/
    public static final String PREFS_SEEDLAND = "seedland";

    /** 请求Url-验证Token登录状态 */
    public static final String API_URL_TOKEN = "1.0/token/refresh";
    /** 请求Url-下发短信激活码 */
    public static final String API_URL_CAPTCHA = "/1.0/sms/send";
    /** 请求Url-手机号注册 */
    public static final String API_URL_REGISTER_PHONE = "/1.0/register";
    /** 请求Url-密码登录 */
    public static final String API_URL_LOGIN_PASSWORD = "/1.0/login/password";
    /** 请求Url-验证码登录 */
    public static final String API_URL_LOGIN_CAPTCHA = "/1.0/login/verify_code";
    /** 请求Url-重置密码 */
    public static final String API_URL_PASSWORD_RESET = "/1.0/password/reset";
    /** 请求Url-修改密码 */
    public static final String API_URL_PASSWORD_MODIFY = "/1.0/password/modify";

    /** 请求返回码-成功 */
    public static final int RESPONSE_CODE_SUCCESS = 0;

    /**
     * 获取字符串资源的值
     * @param id
     * @return
     */
    public static String getString(@StringRes int id) {
        return APP_CONTEXT.getString(id);
    }
}
