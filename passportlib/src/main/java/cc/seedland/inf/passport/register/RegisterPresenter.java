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

class RegisterPresenter extends BasePresenter<ICaptchaView> implements IRegisterPresenter {

    private final RegisterModel model = new RegisterModel();


    @Override
    public void performCaptcha(String phone) {
        int errCode = ValidateUtil.checkPhone(phone);
        if(errCode == Constant.ERROR_CODE_NONE) {
            model.getCaptcha(phone, new BizCallback<BaseBean>(BaseBean.class, view) {

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
        if(checkPassword(password, confirmPassword)){
            model.performPhone(phone, password, captcha, new BizCallback<RegisterBean>(RegisterBean.class, view) {

                @Override
                public void onSuccess(Response<RegisterBean> response) {
                    RegisterBean bean = response.body();
                    BaseViewGuard.callCloseSafely(view, bean.toArgs(), bean.toString());
                }
            });
        }
    }

    // 检验密码
    public boolean checkPassword(String password, String confirm) {

        if(!password.equals(confirm)) {
            BaseViewGuard.callShowToastSafely(view, Constant.getString(R.string.error_password_confirm));
            return false;
        }
        return true;
    }

}
