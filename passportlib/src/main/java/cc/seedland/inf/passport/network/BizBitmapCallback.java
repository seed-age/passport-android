package cc.seedland.inf.passport.network;

import android.graphics.Bitmap;

import com.lzy.okgo.callback.BitmapCallback;
import com.lzy.okgo.model.Response;

import java.lang.ref.WeakReference;

import cc.seedland.inf.passport.base.BaseViewGuard;
import cc.seedland.inf.passport.common.ICaptchaView;

/*
 * <pre>
 *     作者: xuchunlei
 *     联系方式: xuchunlei@seedland.cc / QQ:22003950
 *     时间: 2018/05/17
 *     描述:
 * </pre>
 */
public class BizBitmapCallback extends BitmapCallback {

    private WeakReference<? extends ICaptchaView> view;

    public BizBitmapCallback(WeakReference<? extends ICaptchaView> view) {
        this.view = view;
    }

    @Override
    public void onSuccess(Response<Bitmap> response) {
        if(view != null && view.get() != null) {
            String captchaId = response.headers().get("Captcha-Id");
            view.get().updateImageCaptcha(response.body(), captchaId);
        }
    }

    @Override
    public void onError(Response<Bitmap> response) {
        super.onError(response);
        String msg = "unknown error";
        if(response != null && response.getException() != null) {
            msg = response.getException().getMessage();

        }
        BaseViewGuard.callShowToastSafely(view, msg != null && msg.trim().length() > 0 ? msg : "unknown error");
    }
}
