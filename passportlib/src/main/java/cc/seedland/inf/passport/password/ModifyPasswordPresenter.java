package cc.seedland.inf.passport.password;

import com.lzy.okgo.model.Response;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.base.BasePresenter;
import cc.seedland.inf.passport.base.BaseViewGuard;
import cc.seedland.inf.passport.base.IBaseView;
import cc.seedland.inf.passport.common.LoginBean;
import cc.seedland.inf.passport.network.BizCallback;
import cc.seedland.inf.passport.network.RuntimeCache;
import cc.seedland.inf.passport.util.Constant;
import cc.seedland.inf.passport.util.ValidateUtil;

/**
 * Created by xuchunlei on 2017/11/17.
 */

class ModifyPasswordPresenter extends BasePresenter<IBaseView> {

    private PasswordModel model = new PasswordModel();

    /**
     * 执行修改密码
     * @param origin
     * @param current
     */
    void performModify(String origin, String current, String confirm) {

        int errCode = ValidateUtil.checkPassword(origin);
        if(errCode != Constant.ERROR_CODE_NONE) {
            BaseViewGuard.callShowToastSafely(view, Constant.getString(errCode));
            return;
        }

        errCode = ValidateUtil.checkPasswordConfirm(current, confirm);
        if(errCode != Constant.ERROR_CODE_NONE) {
            BaseViewGuard.callShowToastSafely(view, Constant.getString(errCode));
            return;
        }

        if(origin.equals(current)) {
            BaseViewGuard.callShowToastSafely(view, Constant.getString(R.string.error_password_same));
            return;
        }

        model.modify(origin.trim(), current.trim(), new BizCallback<LoginBean>(LoginBean.class, view) {
            @Override
            public void onSuccess(Response<LoginBean> response) {
                LoginBean bean = response.body();
                RuntimeCache.saveToken(bean.token);
                BaseViewGuard.callCloseSafely(view, bean.toArgs(), bean.raw);
            }
        });

    }
}
