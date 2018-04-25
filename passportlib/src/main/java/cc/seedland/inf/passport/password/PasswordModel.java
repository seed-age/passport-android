package cc.seedland.inf.passport.password;

import android.graphics.Bitmap;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.BitmapCallback;

import java.util.HashMap;
import java.util.Map;

import cc.seedland.inf.passport.base.BaseBean;
import cc.seedland.inf.passport.common.SimpleBean;
import cc.seedland.inf.passport.common.LoginBean;
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
    public void obtainCaptcha(String phone, String imgCaptcha, String imgCaptchaId, BizCallback<BaseBean> callback) {

        Map<String, String> params = new HashMap<>();
        params.put("mobile", phone);
        params.put("captcha_id", imgCaptchaId);
        params.put("captcha_text", imgCaptcha);
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
    public void reset(String phone, String password, String captcha, BizCallback<SimpleBean> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", phone);
        params.put("password", password);
        params.put("code", captcha);

        OkGo.<SimpleBean>post(ApiUtil.generateUrlForPost(Constant.API_URL_PASSWORD_RESET))
                .upRequestBody(ApiUtil.generateParamsBodyForPost(params))
                .execute(callback);
    }

    /**
     * 修改密码
     * <p>
     *     修改密码后获取新token，相当于重新登录
     * </p>
     * @param origin
     * @param current
     * @param callback
     */
    public void modify(String origin, String current, BizCallback<LoginBean> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("sso_tk", RuntimeCache.getToken());
        params.put("old_password", origin);
        params.put("new_password", current);

        OkGo.<LoginBean>post(ApiUtil.generateUrlForPost(Constant.API_URL_PASSWORD_MODIFY))
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
