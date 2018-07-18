package cc.seedland.inf.passport.password;


import org.json.JSONObject;

import cc.seedland.inf.corework.mvp.BasePresenter;
import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.base.BaseViewGuard;
import cc.seedland.inf.passport.base.IPassportView;
import cc.seedland.inf.passport.network.BizCallback;
import cc.seedland.inf.passport.network.RuntimeCache;
import cc.seedland.inf.passport.util.Constant;
import cc.seedland.inf.passport.util.ValidateUtil;

/**
 * Created by xuchunlei on 2017/11/17.
 */

class ModifyPasswordPresenter extends BasePresenter<IPassportView> {

    private PasswordModel model = new PasswordModel();

    public ModifyPasswordPresenter(IPassportView view) {
        super(view);
    }

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

        model.modify(origin.trim(), current.trim(), new BizCallback<IPassportView>(view) {
            @Override
            protected void doSuccess(JSONObject data) throws Throwable {
                RuntimeCache.saveToken(data.getString("sso_tk"));
                BaseViewGuard.callCloseSafely(view, toArgs(data), data.getString("raw"));
            }

        });

    }
}
