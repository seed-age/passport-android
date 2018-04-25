package cc.seedland.inf.passport.common;

import android.graphics.Bitmap;

import cc.seedland.inf.passport.base.IBaseView;

/**
 * Created by xuchunlei on 2017/11/17.
 */

public interface ICaptchaView extends IBaseView {

    /**
     * 等待接收验证码
     */
    void startWaitingCaptcha();

    /**
     * 更新图片验证码
     */
    void updateImageCaptcha(Bitmap code, String captchaId);
}
