package cc.seedland.inf.passport.template.def;

import android.view.View;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.template.LoginCaptchaViewAgent;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/21 15:07
 * 描述 ：
 **/
public class DefaultLoginCaptchaAgent extends LoginCaptchaViewAgent {

    @Override
    public int layout() {
        return R.layout.fragment_login_captcha_default;
    }

    @Override
    public void initViews(View v) {
        super.initViews(v);

    }

}
