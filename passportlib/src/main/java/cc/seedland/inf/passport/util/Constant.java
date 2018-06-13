package cc.seedland.inf.passport.util;

import android.app.Application;
import android.content.Context;
import android.support.annotation.StringRes;

import cc.seedland.inf.passport.BuildConfig;
import cc.seedland.inf.passport.R;

/**
 * 常量类,用于定义一些全局使用的常量
 * Created by xuchunlei on 2017/11/10.
 */

public class Constant {

    private Constant() {

    }

    public static final String TAG = "seedland-passport";
    public static final boolean DEBUG = false;
    /** 应用的Context */
    public static Application APP;
    /**用于保存SDK配置的偏好文件名，保存通用配置参数*/
    public static final String PREFS_SEEDLAND = "seedland";

    /** 请求Url-验证Token登录状态 */
    public static final String API_URL_TOKEN = "/1.0/token/refresh";
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
    /** 请求Url-图形验证码 */
    public static final String API_URL_IMAGE_CAPTCHA = "/1.0/captcha";

    /** 请求返回码-成功 */
    public static final int RESPONSE_CODE_SUCCESS = 0;

    /** OkGo参数-连接／读写等待时间 */
    public static final long WAITTING_MILLISECONDS = 10000;

    /** 参数键值-结果 */
    public static final String EXTRA_KEY_RESULT = "result";
    /** 参数键值-原始结果 */
    public static final String EXTRA_KEY_RAW_RESULT = "raw_result";
    /** 参数键值-请求码 */
    public static final String EXTRA_KEY_REQUEST_CODE = "request_code";
    /** 参数数值-手机号 */
    public static final String EXTRA_KEY_PHONE = "mobile";

    // 错误码
    /** 错误码-没有错误 */
    public static final int ERROR_CODE_NONE = 0;
    /** 错误码-未输入手机号 */
    public static final int ERROR_CODE_PHONE_EMPTY = R.string.error_phone_empty;
    /** 错误码-手机号格式错误 */
    public static final int ERROR_CODE_PHONE_FORMAT = R.string.error_phone_format;
    /** 错误码-未输入验证码 */
    public static final int ERROR_CODE_CAPTCHA_EMPTY = R.string.error_captcha_empty;
    /** 错误码-密码格式错误 */
    public static final int ERROR_CODE_PASSWORD_FORMAT = R.string.error_password_format;
    /** 错误码-未输入密码 */
    public static final int ERROR_CODE_PASSWORD_EMPTY = R.string.error_password_empty;
    /** 错误码-两次密码输入不一致 */
    public static final int ERROR_CODE_PASSWORD_CONFIRM = R.string.error_password_confirm;
    /** 错误码-原密码与旧密码相同 */
    public static final int ERROR_CODE_PASSWORD_SAME = R.string.error_password_same;

    /** 默认的整型值，用于判断非法数据 */
    public static final int NO_INTEGER = -1;

    /**
     * 获取字符串资源的值
     * @param id
     * @return
     */
    public static String getString(@StringRes int id) {
        return APP.getString(id);
    }
}
