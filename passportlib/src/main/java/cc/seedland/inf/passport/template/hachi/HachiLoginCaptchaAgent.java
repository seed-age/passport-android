package cc.seedland.inf.passport.template.hachi;

import android.view.View;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.template.LoginCaptchaViewAgent;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/21 15:07
 * 描述 ：
 **/
public class HachiLoginCaptchaAgent extends LoginCaptchaViewAgent {

    @Override
    public int layout() {
        return R.layout.fragment_login_captcha_hachi;
    }

    @Override
    public void initViews(View v) {
        super.initViews(v);
        PerformTextWatcher watcher = new PerformTextWatcher(performBtn, gainTxv, phoneEdt, imgCaptchaEdt, captchaEdt);

        phoneEdt.addTextChangedListener(watcher);
        imgCaptchaEdt.addTextChangedListener(watcher);
        captchaEdt.addTextChangedListener(watcher);

    }

}
