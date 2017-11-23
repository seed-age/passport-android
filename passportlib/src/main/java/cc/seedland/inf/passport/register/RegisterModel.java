package cc.seedland.inf.passport.register;

import com.lzy.okgo.OkGo;

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
    public void getCaptcha(String phone, BizCallback<BaseBean> callback) {

        Map<String, String> params = new HashMap<>();
        params.put("mobile", phone);
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

}
