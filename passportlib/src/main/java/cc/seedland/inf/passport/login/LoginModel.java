package cc.seedland.inf.passport.login;

import android.graphics.Bitmap;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.BitmapCallback;

import java.util.HashMap;
import java.util.Map;

import cc.seedland.inf.passport.base.BaseBean;
import cc.seedland.inf.passport.common.LoginBean;
import cc.seedland.inf.passport.network.ApiUtil;
import cc.seedland.inf.passport.network.BizCallback;
import cc.seedland.inf.passport.util.Constant;

/**
 * Created by xuchunlei on 2017/11/16.
 */

class LoginModel {

    /**
     * 密码登录
     * @param phone
     * @param password
     * @param callback
     */
    public void loginByPassword(String phone, String password, BizCallback<LoginBean> callback) {

        Map<String, String> params = new HashMap<>();
        params.put("mobile", phone);
        params.put("password", password);

        OkGo.<LoginBean>post(ApiUtil.generateUrlForPost(Constant.API_URL_LOGIN_PASSWORD))
                .upRequestBody(ApiUtil.generateParamsBodyForPost(params))
                .execute(callback);
    }

    /**
     * 验证码登录
     * @param phone
     * @param captcha
     */
    public void loginByCaptcha(String phone, String captcha, BizCallback<LoginBean> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", phone);
        params.put("code", captcha);

        OkGo.<LoginBean>post(ApiUtil.generateUrlForPost(Constant.API_URL_LOGIN_CAPTCHA))
                .upRequestBody(ApiUtil.generateParamsBodyForPost(params))
                .execute(callback);
    }

    /**
     * 获取短信激活码
     * @param phone
     * @param callback
     */
    public void obtainCaptcha(String phone, String imgCaptcha, String imgCaptchaId, BizCallback<BaseBean> callback) {

        Map<String, String> params = new HashMap<>();
        params.put("mobile", phone);
        params.put("captcha_id", imgCaptchaId);
        params.put("captcha_text", imgCaptcha);
        params.put("action", "login");
        OkGo.<BaseBean>post(ApiUtil.generateUrlForPost(Constant.API_URL_CAPTCHA))
                .upRequestBody(ApiUtil.generateParamsBodyForPost(params))
                .execute(callback);

    }

    public void obtainImageCaptcha(BitmapCallback callback) {
        OkGo.<Bitmap>get(ApiUtil.generateUrlForGet(Constant.API_URL_IMAGE_CAPTCHA, null))
                .execute(callback);
    }
}
