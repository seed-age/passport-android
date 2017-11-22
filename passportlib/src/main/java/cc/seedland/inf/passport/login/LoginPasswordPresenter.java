package cc.seedland.inf.passport.login;

import com.lzy.okgo.model.Response;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.base.BasePresenter;
import cc.seedland.inf.passport.base.BaseViewGuard;
import cc.seedland.inf.passport.base.IBaseView;
import cc.seedland.inf.passport.network.BizCallback;
import cc.seedland.inf.passport.network.RuntimeCache;
import cc.seedland.inf.passport.util.Constant;
import cc.seedland.inf.passport.util.ValidateUtil;

/**
 * Created by xuchunlei on 2017/11/8.
 */

class LoginPasswordPresenter extends BasePresenter<IBaseView> {

    private final LoginModel model = new LoginModel();

    /**
     * 密码登录
     * @param phone
     * @param password
     */
    public void perform(String phone, String password) {
        if(!ValidateUtil.checkPhone(phone)) {
            BaseViewGuard.callShowErrorSafely(view, Constant.getString(R.string.error_phone));
            return;
        }

        if(!ValidateUtil.checkPassword(password)) {
            BaseViewGuard.callShowErrorSafely(view, Constant.getString(R.string.error_password));
            return;
        }

        model.loginByPassword(phone, password, new BizCallback<LoginBean>(LoginBean.class, view) {


            @Override
            public void onSuccess(Response<LoginBean> response) {
                LoginBean bean = response.body();
                RuntimeCache.saveToken(bean.token);
                BaseViewGuard.callCloseSafely(view, bean.toArgs(), bean.toString());
            }

        });
    }

}
