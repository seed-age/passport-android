package cc.seedland.inf.passport.login;

import android.graphics.Bitmap;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.BitmapCallback;

import cc.seedland.inf.network.BaseBean;
import cc.seedland.inf.network.Networkit;
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

        OkGo.<LoginBean>post(Networkit.generateFullUrl(Constant.API_URL_LOGIN_PASSWORD))
                .params("mobile", phone)
                .params("password", password)
                .execute(callback);
    }

    /**
     * 验证码登录
     * @param phone
     * @param captcha
     */
    public void loginByCaptcha(String phone, String captcha, BizCallback<LoginBean> callback) {

        OkGo.<LoginBean>post(Networkit.generateFullUrl(Constant.API_URL_LOGIN_CAPTCHA))
                .params("mobile", phone)
                .params("code", captcha)
                .execute(callback);
    }

    /**
     * 获取短信激活码
     * @param phone
     * @param callback
     */
    public void obtainCaptcha(String phone, String imgCaptcha, String imgCaptchaId, BizCallback<BaseBean> callback) {

        OkGo.<BaseBean>post(Networkit.generateFullUrl(Constant.API_URL_CAPTCHA))
                .params("mobile", phone)
                .params("captcha_id", imgCaptchaId)
                .params("captcha_text", imgCaptcha)
                .params("action", "login")
                .execute(callback);

    }

    public void obtainImageCaptcha(BitmapCallback callback) {
        OkGo.<Bitmap>get(Networkit.generateFullUrl(Constant.API_URL_IMAGE_CAPTCHA))
                .execute(callback);
    }
}
