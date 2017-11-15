package cc.seedland.inf.passport.register;

import android.text.TextUtils;
import android.util.Log;

import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.util.Constant;
import cc.seedland.inf.passport.base.BasePresenter;
import cc.seedland.inf.passport.common.CaptchaBean;
import cc.seedland.inf.passport.network.JsonCallback;
import cc.seedland.inf.passport.util.VerficationUtil;

/**
 * Created by xuchunlei on 2017/11/8.
 */

public class RegisterPresenterImpl extends BasePresenter<IRegisterView> implements IRegisterPresenter {

    private final RegisterModel registerModel = new RegisterModel();

    @Override
    public void performCaptcha(String phone) {
        if(checkPhone(phone)) {
            registerModel.getCaptcha(phone, new JsonCallback<CaptchaBean>(CaptchaBean.class) {
                @Override
                public void onStart(Request<CaptchaBean, ? extends Request> request) {
                    super.onStart(request);
                    view.showLoading();
                }

                @Override
                public void onSuccess(Response<CaptchaBean> response) {
                    CaptchaBean bean = response.body();
                    if(bean.code == Constant.RESPONSE_CODE_SUCCESS) {
                        view.startWaitingCaptcha();
                    }else {
                        view.showError(bean.message);
                    }
                }

                @Override
                public void onError(Response<CaptchaBean> response) {
                    super.onError(response);
                    view.showError(response.message());
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                    view.hideLoading();
                }
            });
        }

    }

    @Override
    public void performRegister(String phone, String captcha, String password, String confirmPassword) {
        if(checkPhone(phone)
                && checkCaptcha(captcha)
                && checkPassword(password, confirmPassword)){
            registerModel.performPhone(phone, password, captcha, new JsonCallback<RegisterBean>(RegisterBean.class) {
                @Override
                public void onStart(Request<RegisterBean, ? extends Request> request) {
                    super.onStart(request);
                    view.showLoading();
                }

                @Override
                public void onSuccess(Response<RegisterBean> response) {
                    RegisterBean bean = response.body();
                    if(bean.code == Constant.RESPONSE_CODE_SUCCESS) {
                        view.startWaitingCaptcha();
                    }else {
                        view.showError(bean.message);
                    }
                }

                @Override
                public void onError(Response<RegisterBean> response) {
                    super.onError(response);
                    view.showError(response.message());
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                    view.hideLoading();
                }
            });
        }
    }

    // 检验手机号
    private boolean checkPhone(String phone) {
        if(VerficationUtil.checkNull(phone)) {
            view.showError(Constant.getString(R.string.error_phone));
            return false;
        }

        return true;
    }

    // 检验激活码
    private boolean checkCaptcha(String captcha) {
        if(VerficationUtil.checkNull(captcha)) {
            view.showError(Constant.getString(R.string.error_captcha));
            return false;
        }
        return true;
    }

    // 检验密码
    public boolean checkPassword(String password, String confirm) {

        if(VerficationUtil.checkNull(password)) {
            view.showError(Constant.getString(R.string.error_password));
            return false;
        }

        if(!password.equals(confirm)) {
            view.showError(Constant.getString(R.string.error_password_confirm));
            return false;
        }
        return true;
    }

}
