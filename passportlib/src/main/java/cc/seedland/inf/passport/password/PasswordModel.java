package cc.seedland.inf.passport.password;

import com.lzy.okgo.OkGo;

import java.util.HashMap;
import java.util.Map;

import cc.seedland.inf.passport.base.BaseBean;
import cc.seedland.inf.passport.network.ApiUtil;
import cc.seedland.inf.passport.network.BizCallback;
import cc.seedland.inf.passport.network.RuntimeCache;
import cc.seedland.inf.passport.util.Constant;

/**
 * Created by xuchunlei on 2017/11/17.
 */

class PasswordModel {

    /**
     * 获取短信激活码
     * @param phone
     * @param callback
     */
    public void obtainCaptcha(String phone, BizCallback<BaseBean> callback) {

        Map<String, String> params = new HashMap<>();
        params.put("mobile", phone);
        params.put("action", "resetpwd");
        OkGo.<BaseBean>post(ApiUtil.generateUrlForPost(Constant.API_URL_CAPTCHA))
                .upRequestBody(ApiUtil.generateParamsBodyForPost(params))
                .execute(callback);

    }

    /**
     * 重置密码
     * @param phone
     * @param password
     * @param captcha
     * @param callback
     */
    public void reset(String phone, String password, String captcha, BizCallback<BaseBean> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", phone);
        params.put("password", password);
        params.put("code", captcha);

        OkGo.<BaseBean>post(ApiUtil.generateUrlForPost(Constant.API_URL_PASSWORD_RESET))
                .upRequestBody(ApiUtil.generateParamsBodyForPost(params))
                .execute(callback);
    }

    /**
     * 修改密码
     * @param origin
     * @param current
     * @param callback
     */
    public void modify(String origin, String current, BizCallback<BaseBean> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("sso_tk", RuntimeCache.getToken());
        params.put("old_password", origin);
        params.put("new_password", current);

        OkGo.<BaseBean>post(ApiUtil.generateUrlForPost(Constant.API_URL_PASSWORD_MODIFY))
                .upRequestBody(ApiUtil.generateParamsBodyForPost(params))
                .execute(callback);
    }
}
