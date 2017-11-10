package cc.seedland.inf.passport.register;

/**
 *
 * Created by xuchunlei on 2017/11/8.
 */

public interface IRegisterPresenter {

    /**
     * 执行获取验证码接口
     * @param phone
     */
    void performCaptcha(String phone);

    /**
     * 执行注册接口
     * @param phone
     * @param captcha
     * @param password
     */
    void performRegister(String phone, String captcha, String password);
}
