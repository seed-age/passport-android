package cc.seedland.inf.passport.register;

import com.lzy.okgo.model.Response;

import cc.seedland.inf.network.BaseBean;
import cc.seedland.inf.passport.base.BaseViewGuard;
import cc.seedland.inf.passport.common.ICaptchaView;
import cc.seedland.inf.passport.network.BizBitmapCallback;
import cc.seedland.inf.passport.network.BizCallback;
import cc.seedland.inf.passport.util.Constant;
import cc.seedland.inf.passport.base.BasePresenter;
import cc.seedland.inf.passport.util.ValidateUtil;

/**
 * Created by xuchunlei on 2017/11/8.
 */

class RegisterPresenter extends BasePresenter<ICaptchaView> implements IRegisterPresenter {

    private final RegisterModel model = new RegisterModel();


    @Override
    public void performCaptcha(String phone, String imgCaptcha, String imgCaptchaId) {
        int errCode = ValidateUtil.checkPhone(phone);
        if(errCode == Constant.ERROR_CODE_NONE) {
            errCode = ValidateUtil.checkCaptcha(imgCaptcha);
        }
        if(errCode == Constant.ERROR_CODE_NONE) {
            model.getCaptcha(phone.trim(), imgCaptcha.trim(), imgCaptchaId.trim(), new BizCallback<BaseBean>(BaseBean.class, view) {

                @Override
                public void onSuccess(Response<BaseBean> response) {
                    if(view.get() != null) {
                        view.get().startWaitingCaptcha();
                    }
                }
            });
        }else {
            BaseViewGuard.callShowToastSafely(view, Constant.getString(errCode));
        }

    }

    @Override
    public void performRegister(String phone, String captcha, String password, String confirmPassword) {

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
        errCode = ValidateUtil.checkPassword(password);
        if(errCode != Constant.ERROR_CODE_NONE) {
            BaseViewGuard.callShowToastSafely(view, Constant.getString(errCode));
            return;
        }
        errCode = ValidateUtil.checkPasswordConfirm(password, confirmPassword);
        if(errCode != Constant.ERROR_CODE_NONE) {
            BaseViewGuard.callShowToastSafely(view, Constant.getString(errCode));
            return;
        }
        model.performPhone(phone.trim(), password.trim(), captcha.trim(), new BizCallback<RegisterBean>(RegisterBean.class, view) {

            @Override
            public void onSuccess(Response<RegisterBean> response) {
                RegisterBean bean = response.body();
                BaseViewGuard.callCloseSafely(view, bean.toArgs(), bean.toString());
            }
        });
    }

    @Override
    public void performImageCaptcha() {
        model.obtainImageCaptcha(new BizBitmapCallback(view));
    }

}
