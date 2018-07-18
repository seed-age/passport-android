package cc.seedland.inf.passport.login;


import org.json.JSONObject;

import cc.seedland.inf.corework.mvp.BasePresenter;
import cc.seedland.inf.passport.base.BaseViewGuard;
import cc.seedland.inf.passport.common.ICaptchaView;
import cc.seedland.inf.passport.network.BizBitmapCallback;
import cc.seedland.inf.passport.network.BizCallback;
import cc.seedland.inf.passport.network.RuntimeCache;
import cc.seedland.inf.passport.util.Constant;
import cc.seedland.inf.passport.util.ValidateUtil;

/**
 * Created by xuchunlei on 2017/11/17.
 */

class LoginCaptchaPresenter extends BasePresenter<ICaptchaView> {

    private LoginModel model = new LoginModel();

    public LoginCaptchaPresenter(ICaptchaView view) {
        super(view);
    }

    /**
     * 执行获取验证码接口
     * @param phone
     */
    void performCaptcha(String phone, String imgCaptcha, String imgCaptchaId) {

        int errCode = ValidateUtil.checkPhone(phone);
        if(errCode == Constant.ERROR_CODE_NONE) {
            errCode = ValidateUtil.checkCaptcha(imgCaptcha);
        }

        if(errCode == Constant.ERROR_CODE_NONE) {
            model.obtainCaptcha(phone.trim(), imgCaptcha.trim(), imgCaptchaId.trim(), new BizCallback<ICaptchaView>(view) {

                @Override
                protected void doSuccess(JSONObject data) {
                    if(view.get() != null) {
                        view.get().showToast(Constant.getString(Constant.TIP_CAPTCHA_SEND));
                        view.get().startWaitingCaptcha();
                    }
                }
            });
        }else {
            BaseViewGuard.callShowToastSafely(view, Constant.getString(errCode));
        }
    }

    /**
     * 登录
     * @param phone
     * @param captcha
     */
    public void perform(String phone, String captcha) {
        int errCode = ValidateUtil.checkPhone(phone);
        if(errCode != Constant.ERROR_CODE_NONE) {
            BaseViewGuard.callShowToastSafely(view, Constant.getString(errCode));
            return;
        }

        errCode = ValidateUtil.checkCaptcha(captcha);
        if(errCode != Constant.ERROR_CODE_NONE) {
            BaseViewGuard.callShowToastSafely(view, Constant.getString(errCode));
            return;
        }

        model.loginByCaptcha(phone, captcha, new BizCallback<ICaptchaView>(view) {
            @Override
            protected void doSuccess(JSONObject data) throws Throwable {
                RuntimeCache.saveToken(data.getString("sso_tk"));
                RuntimeCache.savePhone(data.getString("mobile"));
                BaseViewGuard.callCloseSafely(view, toArgs(data), data.getString("raw"));
            }
        });
    }

    public void performImageCaptcha() {
        model.obtainImageCaptcha(new BizBitmapCallback(view));
    }
}
