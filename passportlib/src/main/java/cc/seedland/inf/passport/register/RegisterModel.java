package cc.seedland.inf.passport.register;

import com.lzy.okgo.OkGo;

import java.util.HashMap;
import java.util.Map;

import cc.seedland.inf.passport.util.Constant;
import cc.seedland.inf.passport.common.CaptchaBean;
import cc.seedland.inf.passport.network.ApiFactory;
import cc.seedland.inf.passport.network.JsonCallback;

/**
 * Created by xuchunlei on 2017/11/10.
 */

public class RegisterModel {

    public void getCaptcha(String phone, JsonCallback<CaptchaBean> callback) {

        OkGo.<CaptchaBean>post(ApiFactory.generateUrlForPost(Constant.API_URL_CAPTCHA))
                .params("mobile", phone)
                .params("action", "reg")
                .execute(callback);

    }
}
