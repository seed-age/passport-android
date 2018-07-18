package cc.seedland.inf.passport.login;


import org.json.JSONObject;

import cc.seedland.inf.corework.mvp.BasePresenter;
import cc.seedland.inf.passport.base.BaseViewGuard;
import cc.seedland.inf.passport.network.BizCallback;
import cc.seedland.inf.passport.network.RuntimeCache;
import cc.seedland.inf.passport.util.Constant;
import cc.seedland.inf.passport.util.ValidateUtil;

/**
 * Created by xuchunlei on 2017/11/8.
 */

class LoginPasswordPresenter extends BasePresenter<ILoginMainView> {

    private LoginModel model = new LoginModel();

    public LoginPasswordPresenter(ILoginMainView view) {
        super(view);
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

        model.loginByPassword(phone.trim(), password.trim(), new BizCallback<ILoginMainView>(view) {
            @Override
            protected void doSuccess(JSONObject data) throws Throwable {
                RuntimeCache.saveToken(data.getString("sso_tk"));
                RuntimeCache.savePhone(data.getString("mobile"));
                BaseViewGuard.callCloseSafely(view, toArgs(data), data.getString("raw"));
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
