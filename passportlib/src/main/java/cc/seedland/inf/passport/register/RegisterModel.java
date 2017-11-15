package cc.seedland.inf.passport.register;

import android.support.annotation.StringRes;

import com.lzy.okgo.OkGo;

import java.util.HashMap;
import java.util.Map;

import cc.seedland.inf.passport.util.Constant;
import cc.seedland.inf.passport.common.CaptchaBean;
import cc.seedland.inf.passport.network.ApiUtil;
import cc.seedland.inf.passport.network.JsonCallback;

/**
 * 注册模型类
 * Created by xuchunlei on 2017/11/10.
 */

public class RegisterModel {

    /**
     * 获取短信激活码
     * @param phone
     * @param callback
     */
    public void getCaptcha(String phone, JsonCallback<CaptchaBean> callback) {

        Map<String, String> params = new HashMap<>();
        params.put("mobile", phone);
        params.put("action", "reg");
        OkGo.<CaptchaBean>post(ApiUtil.generateUrlForPost(Constant.API_URL_CAPTCHA))
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
    public void performPhone(String phone, String password, String captcha, JsonCallback<RegisterBean> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", phone);
        params.put("password", password);
        params.put("code", captcha);

        OkGo.<RegisterBean>post(ApiUtil.generateUrlForPost(Constant.API_URL_REGISTER_PHONE))
                .upRequestBody(ApiUtil.generateParamsBodyForPost(params))
                .execute(callback);
    }

}
