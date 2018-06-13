package cc.seedland.inf.passport.password;

import android.graphics.Bitmap;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.BitmapCallback;

import java.util.HashMap;
import java.util.Map;

import cc.seedland.inf.network.BaseBean;
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

        OkGo.<BaseBean>post(ApiUtil.generateFullUrl(Constant.API_URL_CAPTCHA))
                .params("mobile", phone)
                .params("captcha_id", imgCaptchaId)
                .params("captcha_text", imgCaptcha)
                .params("action", "resetpwd")
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

        OkGo.<SimpleBean>post(ApiUtil.generateFullUrl(Constant.API_URL_PASSWORD_RESET))
                .params("mobile", phone)
                .params("password", password)
                .params("code", captcha)
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

        OkGo.<LoginBean>post(ApiUtil.generateFullUrl(Constant.API_URL_PASSWORD_MODIFY))
                .params("sso_tk", RuntimeCache.getToken())
                .params("old_password", origin)
                .params("new_password", current)
                .execute(callback);
    }

    /**
     * 获取图形验证码
     * @param callback
     */
    public void obtainImageCaptcha(BitmapCallback callback) {
        OkGo.<Bitmap>get(ApiUtil.generateFullUrl(Constant.API_URL_IMAGE_CAPTCHA))
                .execute(callback);
    }
}
