package cc.seedland.inf.passport.password;

import com.lzy.okgo.model.Response;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.base.BaseBean;
import cc.seedland.inf.passport.base.BasePresenter;
import cc.seedland.inf.passport.base.BaseViewGuard;
import cc.seedland.inf.passport.common.ICaptchaView;
import cc.seedland.inf.passport.network.BizCallback;
import cc.seedland.inf.passport.util.Constant;
import cc.seedland.inf.passport.util.ValidateUtil;

/**
 * Created by xuchunlei on 2017/11/16.
 */

class ResetPasswordPresenter extends BasePresenter<ICaptchaView> {

    PasswordModel model = new PasswordModel();

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
            BaseViewGuard.callShowErrorSafely(view, Constant.getString(R.string.error_phone));
        }
    }

    /**
     * 执行重置密码
     * @param phone
     * @param password
     * @param confirm
     * @param captcha
     */
    void performReset(String phone, String password, String confirm, String captcha) {
        if(!ValidateUtil.checkPhone(phone)) {
            BaseViewGuard.callShowErrorSafely(view, Constant.getString(R.string.error_phone));
            return;
        }
        if(ValidateUtil.checkNull(captcha)) {
            BaseViewGuard.callShowErrorSafely(view, Constant.getString(R.string.error_captcha));
            return;
        }
        if(!ValidateUtil.checkPasswordConfirm(password, confirm)) {
            BaseViewGuard.callShowErrorSafely(view, Constant.getString(R.string.error_password_confirm));
            return;
        }

        model.reset(phone, password, captcha, new BizCallback<BaseBean>(BaseBean.class, view) {
            @Override
            public void onSuccess(Response<BaseBean> response) {
                BaseViewGuard.callCloseSafely(view, null, null);
            }
        });

    }
}
