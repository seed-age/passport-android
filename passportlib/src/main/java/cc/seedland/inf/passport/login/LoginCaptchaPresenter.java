package cc.seedland.inf.passport.login;

import android.os.Bundle;

import com.lzy.okgo.model.Response;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.base.BaseBean;
import cc.seedland.inf.passport.base.BasePresenter;
import cc.seedland.inf.passport.base.BaseViewGuard;
import cc.seedland.inf.passport.common.ICaptchaView;
import cc.seedland.inf.passport.network.BizCallback;
import cc.seedland.inf.passport.network.RuntimeCache;
import cc.seedland.inf.passport.util.Constant;
import cc.seedland.inf.passport.util.ValidateUtil;

/**
 * Created by xuchunlei on 2017/11/17.
 */

class LoginCaptchaPresenter extends BasePresenter<ICaptchaView> {

    private LoginModel model = new LoginModel();

    /**
     * 执行获取验证码接口
     * @param phone
     */
    void performCaptcha(String phone) {
        if(ValidateUtil.checkPhone(phone)) {
            model.obtainCaptcha(phone, new BizCallback<BaseBean>(BaseBean.class, view) {
                @Override
                public void onSuccess(Response<BaseBean> response) {
                    if(view.get() != null) {
                        view.get().startWaitingCaptcha();
                    }
                }
            });
        }else {
            BaseViewGuard.callShowToastSafely(view, Constant.getString(R.string.error_phone));
        }
    }

    /**
     * 登录
     * @param phone
     * @param captcha
     */
    public void perform(String phone, String captcha) {
        if(!ValidateUtil.checkPhone(phone)) {
            BaseViewGuard.callShowToastSafely(view, Constant.getString(R.string.error_phone));
            return;
        }

        if(ValidateUtil.checkNull(captcha)) {
            BaseViewGuard.callShowToastSafely(view, Constant.getString(R.string.error_captcha));
            return;
        }

        model.loginByCaptcha(phone, captcha, new BizCallback<LoginBean>(LoginBean.class, view) {


            @Override
            public void onSuccess(Response<LoginBean> response) {
                LoginBean bean = response.body();
                RuntimeCache.saveToken(bean.token);

                BaseViewGuard.callCloseSafely(view, bean.toArgs(), bean.toString());
            }

        });
    }
}
