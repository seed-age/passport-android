package cc.seedland.inf.passport.template.hachi;

import android.view.View;
import android.widget.EditText;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.template.RegisterViewAgent;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/22 16:46
 * 描述 ：
 **/
public class HachiRegisterAgent extends RegisterViewAgent {

    @Override
    public int layout() {
        return R.layout.fragment_register_hachi;
    }

    @Override
    public void initViews(View v) {
        super.initViews(v);

        PerformTextWatcher watcher = new PerformTextWatcher(performBtn, gainBtn, phoneEdt, imgCaptchaEdt, captchaEdt, passwordEdt);

        phoneEdt.addTextChangedListener(watcher);
        passwordEdt.addTextChangedListener(watcher);
        imgCaptchaEdt.addTextChangedListener(watcher);
        captchaEdt.addTextChangedListener(watcher);
    }
}
