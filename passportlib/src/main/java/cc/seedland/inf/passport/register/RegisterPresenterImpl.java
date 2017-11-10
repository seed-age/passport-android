package cc.seedland.inf.passport.register;

import android.util.Log;

import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import cc.seedland.inf.passport.Constant;
import cc.seedland.inf.passport.base.BasePresenter;
import cc.seedland.inf.passport.common.CaptchaBean;
import cc.seedland.inf.passport.network.JsonCallback;

/**
 * Created by xuchunlei on 2017/11/8.
 */

public class RegisterPresenterImpl extends BasePresenter<IRegisterView> implements IRegisterPresenter {

    private final RegisterModel registerModel = new RegisterModel();

    @Override
    public void performCaptcha(String phone) {
        registerModel.getCaptcha(phone, new JsonCallback<CaptchaBean>() {
            @Override
            public void onStart(Request<CaptchaBean, ? extends Request> request) {
                super.onStart(request);
                view.showLoading();
            }

            @Override
            public void onSuccess(Response<CaptchaBean> response) {
                Log.e(Constant.TAG, response.message());
            }

            @Override
            public void onError(Response<CaptchaBean> response) {
                super.onError(response);
                view.showError(response.message());
            }
        });
    }

    @Override
    public void performRegister(String phone, String captcha, String password) {

    }
}
