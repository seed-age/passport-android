package cc.seedland.inf.passport.register;

import com.lzy.okgo.model.Response;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.base.BaseBean;
import cc.seedland.inf.passport.base.BaseViewGuard;
import cc.seedland.inf.passport.common.ICaptchaView;
import cc.seedland.inf.passport.network.BizCallback;
import cc.seedland.inf.passport.util.Constant;
import cc.seedland.inf.passport.base.BasePresenter;
import cc.seedland.inf.passport.util.ValidateUtil;

/**
 * Created by xuchunlei on 2017/11/8.
 */

public class RegisterPresenter extends BasePresenter<ICaptchaView> implements IRegisterPresenter {

    private final RegisterModel model = new RegisterModel();


    @Override
    public void performCaptcha(String phone) {
        if(checkPhone(phone)) {
            model.getCaptcha(phone, new BizCallback<BaseBean>(BaseBean.class, view) {

                @Override
                public void onSuccess(Response<BaseBean> response) {
                    if(view.get() != null) {
                        view.get().startWaitingCaptcha();
                    }
                }
            });
        }

    }

    @Override
    public void performRegister(String phone, String captcha, String password, String confirmPassword) {
        if(checkPhone(phone)
                && checkCaptcha(captcha)
                && checkPassword(password, confirmPassword)){
            model.performPhone(phone, password, captcha, new BizCallback<RegisterBean>(RegisterBean.class, view) {

                @Override
                public void onSuccess(Response<RegisterBean> response) {
                    RegisterBean bean = response.body();
                    BaseViewGuard.callCloseSafely(view, bean.toArgs(), bean.toString());
                }
            });
        }
    }

    // 检验手机号
    private boolean checkPhone(String phone) {
        if(ValidateUtil.checkNull(phone)) {
            BaseViewGuard.callShowErrorSafely(view, Constant.getString(R.string.error_phone));
            return false;
        }

        return true;
    }

    // 检验激活码
    private boolean checkCaptcha(String captcha) {
        if(ValidateUtil.checkNull(captcha)) {
            BaseViewGuard.callShowErrorSafely(view, Constant.getString(R.string.error_captcha));
            return false;
        }
        return true;
    }

    // 检验密码
    public boolean checkPassword(String password, String confirm) {

        if(ValidateUtil.checkNull(password)) {
            BaseViewGuard.callShowErrorSafely(view, Constant.getString(R.string.error_password));
            return false;
        }

        if(!password.equals(confirm)) {
            BaseViewGuard.callShowErrorSafely(view, Constant.getString(R.string.error_password_confirm));
            return false;
        }
        return true;
    }

}
