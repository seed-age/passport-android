package cc.seedland.inf.passport.password;

import com.lzy.okgo.model.Response;

import cc.seedland.inf.corework.mvp.BasePresenter;
import cc.seedland.inf.network.BaseBean;
import cc.seedland.inf.passport.base.BaseViewGuard;
import cc.seedland.inf.passport.common.ICaptchaView;
import cc.seedland.inf.passport.common.SimpleBean;
import cc.seedland.inf.passport.network.BizBitmapCallback;
import cc.seedland.inf.passport.network.BizCallback;
import cc.seedland.inf.passport.util.Constant;
import cc.seedland.inf.passport.util.ValidateUtil;

/**
 * Created by xuchunlei on 2017/11/16.
 */

class ResetPasswordPresenter extends BasePresenter<ICaptchaView> {

    PasswordModel model = new PasswordModel();

    public ResetPasswordPresenter(ICaptchaView view) {
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
            model.obtainCaptcha(phone.trim(), imgCaptcha.trim(), imgCaptchaId.trim(), new BizCallback<BaseBean>(BaseBean.class, view) {
                @Override
                public void onSuccess(Response<BaseBean> response) {
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
     * 执行重置密码
     * @param phone
     * @param password
     * @param confirm
     * @param captcha
     */
    void performReset(String phone, String password, String confirm, String captcha) {
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
        if(confirm != null) {
            errCode = ValidateUtil.checkPasswordConfirm(password, confirm);
        }
        if(errCode != Constant.ERROR_CODE_NONE) {
            BaseViewGuard.callShowToastSafely(view, Constant.getString(errCode));
            return;
        }

        model.reset(phone.trim(), password.trim(), captcha.trim(), new BizCallback<SimpleBean>(SimpleBean.class, view) {
            @Override
            public void onSuccess(Response<SimpleBean> response) {
                SimpleBean bean = response.body();
                BaseViewGuard.callCloseSafely(view, bean.toArgs(), bean.toString());
            }
        });

    }

    /**
     * 执行获取图形验证码
     */
    public void performImageCaptcha() {
        model.obtainImageCaptcha(new BizBitmapCallback(view));
    }
}
