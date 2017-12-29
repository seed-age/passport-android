package cc.seedland.inf.passport.login;

import com.lzy.okgo.model.Response;

import cc.seedland.inf.passport.base.BasePresenter;
import cc.seedland.inf.passport.base.BaseViewGuard;
import cc.seedland.inf.passport.common.LoginBean;
import cc.seedland.inf.passport.network.BizCallback;
import cc.seedland.inf.passport.network.RuntimeCache;
import cc.seedland.inf.passport.util.Constant;
import cc.seedland.inf.passport.util.ValidateUtil;

/**
 * Created by xuchunlei on 2017/11/8.
 */

class LoginPasswordPresenter extends BasePresenter<ILoginMainView> {

    private LoginModel model;

    public LoginPasswordPresenter(LoginModel model) {
        this.model = model;
    }

    /**
     * 密码登录
     * @param phone
     * @param password
     */
    public void perform(String phone, String password) {
        int errCode = ValidateUtil.checkPhone(phone);
        if(errCode != Constant.ERROR_CODE_NONE) {
            BaseViewGuard.callShowToastSafely(view, Constant.getString(errCode));
            return;
        }

        errCode = ValidateUtil.checkPassword(password);
        if(errCode != Constant.ERROR_CODE_NONE) {
            BaseViewGuard.callShowToastSafely(view, Constant.getString(errCode));
            return;
        }

        model.loginByPassword(phone.trim(), password.trim(), new BizCallback<LoginBean>(LoginBean.class, view) {


            @Override
            public void onSuccess(Response<LoginBean> response) {
                super.onSuccess(response);
                LoginBean bean = response.body();
                RuntimeCache.saveToken(bean.token);
                RuntimeCache.savePhone(bean.mobile);
                BaseViewGuard.callCloseSafely(view, bean.toArgs(), bean.toString());
            }

        });
    }

    public void refreshPhone(String phone, String info) {
        BaseViewGuard.callShowTipSafely(view, info);
        if(view.get() != null && ValidateUtil.checkPhone(phone) == Constant.ERROR_CODE_NONE) {
            view.get().loadPhone(phone);
        }
    }

}
