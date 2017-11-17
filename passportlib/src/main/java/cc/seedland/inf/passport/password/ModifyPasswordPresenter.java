package cc.seedland.inf.passport.password;

import android.util.Log;

import com.lzy.okgo.model.Response;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.base.BaseBean;
import cc.seedland.inf.passport.base.BasePresenter;
import cc.seedland.inf.passport.base.BaseViewGuard;
import cc.seedland.inf.passport.base.IBaseView;
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

        if(!ValidateUtil.checkPassword(origin)) {
            BaseViewGuard.callShowErrorSafely(view, Constant.getString(R.string.error_password));
            return;
        }

        if(!ValidateUtil.checkPasswordConfirm(current, confirm)) {
            BaseViewGuard.callShowErrorSafely(view, Constant.getString(R.string.error_password_confirm));
            return;
        }

        model.modify(origin, current, new BizCallback<BaseBean>(BaseBean.class, view) {
            @Override
            public void onSuccess(Response<BaseBean> response) {
                RuntimeCache.saveToken("");
                if(view.get() != null) {
                    view.get().close();
                }
            }
        });

    }
}
