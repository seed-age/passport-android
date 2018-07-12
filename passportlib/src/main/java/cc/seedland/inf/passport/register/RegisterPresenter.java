package cc.seedland.inf.passport.register;

import android.text.TextUtils;

import com.lzy.okgo.model.Response;

import cc.seedland.inf.corework.mvp.BasePresenter;
import cc.seedland.inf.network.BaseBean;
import cc.seedland.inf.passport.config.ChannelInfoBean;
import cc.seedland.inf.passport.base.BaseViewGuard;
import cc.seedland.inf.passport.common.LoginBean;
import cc.seedland.inf.passport.config.Config;
import cc.seedland.inf.passport.network.BizBitmapCallback;
import cc.seedland.inf.passport.network.BizCallback;
import cc.seedland.inf.passport.network.RuntimeCache;
import cc.seedland.inf.passport.util.Constant;
import cc.seedland.inf.passport.util.ValidateUtil;

/**
 * Created by xuchunlei on 2017/11/8.
 */

class RegisterPresenter extends BasePresenter<IRegisterView> implements IRegisterPresenter {

    private final RegisterModel model = new RegisterModel();

    public RegisterPresenter(IRegisterView view) {
        super(view);
    }


    @Override
    public void performCaptcha(String phone, String imgCaptcha, String imgCaptchaId) {
        int errCode = ValidateUtil.checkPhone(phone);
        if(errCode == Constant.ERROR_CODE_NONE) {
            errCode = ValidateUtil.checkCaptcha(imgCaptcha);
        }
        if(errCode == Constant.ERROR_CODE_NONE) {
            model.getCaptcha(phone.trim(), imgCaptcha.trim(), imgCaptchaId.trim(), new BizCallback<BaseBean>(BaseBean.class, view) {

                @Override
                public void onSuccess(Response<BaseBean> response) {
                    if(view.get() != null) {
                        view.get().showToast(Constant.getString(Constant.TIP_CAPTCHA_SEND));
                        view.get().startWaitingCaptcha();
                    }
                }
            });
        }else {
            BaseViewGuard.callShowToastSafely(view, Constant.getString(errCode));
        }

    }

    @Override
    public void performRegister(String phone, String captcha, String password, String confirm) {

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
        if(confirm != null) {
            errCode = ValidateUtil.checkPasswordConfirm(password, confirm);
        }
        if(errCode != Constant.ERROR_CODE_NONE) {
            BaseViewGuard.callShowToastSafely(view, Constant.getString(errCode));
            return;
        }
        model.performPhone(phone.trim(), password.trim(), captcha.trim(), new BizCallback<LoginBean>(LoginBean.class, view) {

            @Override
            public void onSuccess(Response<LoginBean> response) {
                LoginBean bean = response.body();
                RuntimeCache.saveToken(bean.token);
                RuntimeCache.savePhone(bean.mobile);
                BaseViewGuard.callCloseSafely(view, bean.toArgs(), bean.toString());
            }
        });
    }

    @Override
    public void performImageCaptcha() {
        model.obtainImageCaptcha(new BizBitmapCallback(view));
    }

    @Override
    public void performAgreement() {

        if(getView() != null) {
            getView().showLoading();
        }

        Config.get().getAgreement(new Config.ConfigCallback() {
            @Override
            public void onConfigReceived(ChannelInfoBean info) {
                if (getView() != null) {
                    getView().hideLoading();
                    if(!info.channelProtocol.isEmpty()) {
                        getView().showAgreement(info.channelProtocol);
                    }

                }
            }
        });

//        model.obtainAgreement(new BizCallback<ChannelInfoBean>(ChannelInfoBean.class, view) {
//            @Override
//            public void onSuccess(Response<ChannelInfoBean> response) {
//                super.onSuccess(response);
//                ChannelInfoBean agreement = response.body();
//                if(!agreement.channelProtocol.isEmpty()) {
//                    if(getView() != null) {
//                        getView().showAgreement(agreement.channelProtocol);
//                    }else {
////                        BaseViewGuard.callShowToastSafely(view, "没有用户协议");
//                    }
//                }
//            }
//        });
    }


}
