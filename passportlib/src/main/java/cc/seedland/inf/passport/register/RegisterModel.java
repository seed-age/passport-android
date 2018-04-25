package cc.seedland.inf.passport.register;

import android.graphics.Bitmap;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.BitmapCallback;

import java.util.HashMap;
import java.util.Map;

import cc.seedland.inf.passport.base.BaseBean;
import cc.seedland.inf.passport.network.BizCallback;
import cc.seedland.inf.passport.util.Constant;
import cc.seedland.inf.passport.network.ApiUtil;

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
    public void getCaptcha(String phone, String imgCaptcha, String imgCaptchaId, BizCallback<BaseBean> callback) {

        Map<String, String> params = new HashMap<>();
        params.put("mobile", phone);
        params.put("captcha_id", imgCaptchaId);
        params.put("captcha_text", imgCaptcha);
        params.put("action", "reg");
        OkGo.<BaseBean>post(ApiUtil.generateUrlForPost(Constant.API_URL_CAPTCHA))
                .upRequestBody(ApiUtil.generateParamsBodyForPost(params))
                .execute(callback);

    }

    /**
     * 手机号注册
     * @param phone
     * @param password
     * @param captcha
     * @param callback
     */
    public void performPhone(String phone, String password, String captcha, BizCallback<RegisterBean> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", phone);
        params.put("password", password);
        params.put("code", captcha);

        OkGo.<RegisterBean>post(ApiUtil.generateUrlForPost(Constant.API_URL_REGISTER_PHONE))
                .upRequestBody(ApiUtil.generateParamsBodyForPost(params))
                .execute(callback);
    }

    /**
     * 获取图形验证码
     * @param callback
     */
    public void obtainImageCaptcha(BitmapCallback callback) {
        OkGo.<Bitmap>get(ApiUtil.generateUrlForGet(Constant.API_URL_IMAGE_CAPTCHA, null))
                .execute(callback);
    }

}
