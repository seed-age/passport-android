package cc.seedland.inf.passport.template.hachi;

import android.view.View;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.template.ResetPasswordViewAgent;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/22 17:07
 * 描述 ：
 **/
public class HachiResetPasswordAgent extends ResetPasswordViewAgent {

    @Override
    public int layout() {
        return R.layout.fragment_password_reset_hachi;
    }

    @Override
    public void initViews(View v) {
        super.initViews(v);

        PerformTextWatcher watcher = new PerformTextWatcher(performBtn, captchaBtn, phoneEdt, imgCaptchaEdt, captchaEdt, passwordEdt);
        phoneEdt.addTextChangedListener(watcher);
        imgCaptchaEdt.addTextChangedListener(watcher);
        captchaEdt.addTextChangedListener(watcher);
        passwordEdt.addTextChangedListener(watcher);
    }
}
