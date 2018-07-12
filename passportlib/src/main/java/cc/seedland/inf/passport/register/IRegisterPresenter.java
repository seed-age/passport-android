package cc.seedland.inf.passport.register;

/**
 *
 * Created by xuchunlei on 2017/11/8.
 */

interface IRegisterPresenter {

    /**
     * 执行获取验证码接口
     * @param phone
     */
    void performCaptcha(String phone, String imgCaptcha, String imgCaptchaId);

    /**
     * 执行注册接口
     * @param phone
     * @param captcha
     * @param password
     */
    void performRegister(String phone, String captcha, String password, String confirmPassword);

    /**
     * 执行获取图形验证码接口
     */
    void performImageCaptcha();

    /**
     * 执行获取用户协议的接口
     */
    void performAgreement();

}
