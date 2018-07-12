package cc.seedland.inf.passport.register;

import cc.seedland.inf.passport.common.ICaptchaView;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/29 17:20
 * 描述 ：
 **/
public interface IRegisterView extends ICaptchaView {

    void showAgreement(String url);
}
