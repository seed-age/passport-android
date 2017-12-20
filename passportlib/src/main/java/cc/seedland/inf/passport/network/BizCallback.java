package cc.seedland.inf.passport.network;

import android.view.View;

import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.lang.ref.WeakReference;

import cc.seedland.inf.passport.base.BaseBean;
import cc.seedland.inf.passport.base.BaseViewGuard;
import cc.seedland.inf.passport.base.IBaseView;
import cc.seedland.inf.passport.util.Constant;
import cc.seedland.inf.passport.util.LogUtil;

/**
 * Created by xuchunlei on 2017/11/16.
 */

public class BizCallback<T extends BaseBean> extends JsonCallback<T> {

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
        BaseViewGuard.callShowToastSafely(view, response.getException().getMessage());
    }

    @Override
    public void onFinish() {
        super.onFinish();
        BaseViewGuard.callHideloadingSafely(view);
    }
}
