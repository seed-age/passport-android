package cc.seedland.inf.passport.register;

import android.graphics.Bitmap;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.BitmapCallback;

import org.json.JSONObject;

import cc.seedland.inf.network.Networkit;
import cc.seedland.inf.passport.network.BizCallback;
import cc.seedland.inf.passport.util.Constant;

/**
 * 注册模型类
 * Created by xuchunlei on 2017/11/10.
 */

class RegisterModel {

    /**
     * 获取短信激活码
     * @param phone
     * @param callback
     */
    public void getCaptcha(String phone, String imgCaptcha, String imgCaptchaId, BizCallback<IRegisterView> callback) {

        OkGo.<JSONObject>post(Networkit.generateFullUrl(Constant.API_URL_CAPTCHA))
                .params("mobile", phone)
                .params("captcha_id", imgCaptchaId)
                .params("captcha_text", imgCaptcha)
                .params("action", "reg")
                .execute(callback);

    }

    /**
     * 手机号注册
     * @param phone
     * @param password
     * @param captcha
     * @param callback
     */
    public void performPhone(String phone, String password, String captcha, BizCallback<IRegisterView> callback) {

        OkGo.<JSONObject>post(Networkit.generateFullUrl(Constant.API_URL_REGISTER_PHONE))
                .params("mobile", phone)
                .params("password", password)
                .params("code", captcha)
                .execute(callback);
    }

    /**
     * 获取图形验证码
     * @param callback
     */
    public void obtainImageCaptcha(BitmapCallback callback) {
        OkGo.<Bitmap>get(Networkit.generateFullUrl(Constant.API_URL_IMAGE_CAPTCHA))
                .execute(callback);
    }

}
