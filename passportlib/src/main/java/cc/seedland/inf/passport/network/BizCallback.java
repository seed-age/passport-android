package cc.seedland.inf.passport.network;

import android.view.View;

import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.lang.ref.WeakReference;

import cc.seedland.inf.network.BaseBean;
import cc.seedland.inf.network.SeedCallback;
import cc.seedland.inf.passport.base.BaseViewGuard;
import cc.seedland.inf.passport.base.IBaseView;
import cc.seedland.inf.passport.util.Constant;
import cc.seedland.inf.passport.util.LogUtil;

/**
 * Created by xuchunlei on 2017/11/16.
 */

public class BizCallback<T extends BaseBean> extends SeedCallback<T> {

    private WeakReference<? extends IBaseView> view;

    public BizCallback(Class<T> clz, WeakReference<? extends IBaseView> view) {
        super(clz);
        this.view = view;
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        BaseViewGuard.callShowloadingSafely(view);

    }

    @Override
    public void onSuccess(Response<T> response) {
        LogUtil.d(Constant.TAG, "api-response:" + response.body().toString());
    }

    @Override
    public void onError(Response<T> response) {
        super.onError(response);
        String msg = "unknown error";
        if(response != null && response.getException() != null) {
            msg = response.getException().getMessage();

        }
        BaseViewGuard.callShowToastSafely(view, msg != null && msg.trim().length() > 0 ? msg : "unknown error");
    }

    @Override
    public void onFinish() {
        super.onFinish();
        BaseViewGuard.callHideloadingSafely(view);
    }
}
