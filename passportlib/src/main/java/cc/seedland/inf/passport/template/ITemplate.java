package cc.seedland.inf.passport.template;

import android.support.annotation.LayoutRes;

import java.util.HashSet;
import java.util.Set;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/21 10:38
 * 描述 ：
 **/
public interface ITemplate {

    LoginMainViewAgent createLoginMainAgent();

    LoginCaptchaViewAgent createLoginCaptchaAgent();

    <T extends IViewAgent> T createAgent(String clzName);

    @LayoutRes int createLayout(String clzName);

}
