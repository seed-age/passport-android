package cc.seedland.inf.passport.template.hachi;

import android.view.View;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.stat.PassportStatAgent;
import cc.seedland.inf.passport.template.LoginMainViewAgent;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/21 11:18
 * 描述 ：
 **/
public class HachiLoginMainAgent extends LoginMainViewAgent {


    @Override
    public int layout() {
        return R.layout.fragment_login_password_hachi;
    }

    @Override
    public void initViews(View v) {
        super.initViews(v);

        PerformTextWatcher watcher = new PerformTextWatcher(performBtn, phoneEdt, passwordEdt);

        phoneEdt.addTextChangedListener(watcher);
        passwordEdt.addTextChangedListener(watcher);
    }

    @Override
    public void onShow() {
        super.onShow();
        PassportStatAgent.get().onLoginPasswordPage();
    }
}
