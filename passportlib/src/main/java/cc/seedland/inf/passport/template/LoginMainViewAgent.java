package cc.seedland.inf.passport.template;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cc.seedland.inf.passport.R;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/21 14:07
 * 描述 ：界面模版-登录主界面代理，为子类屏蔽无需实现的方法
 **/
public abstract class LoginMainViewAgent implements IViewAgent {

    public EditText phoneEdt;
    public EditText passwordEdt;
    public Button performBtn;
    public View captchaTxv;

    @Override
    public void initViews(View v) {
        phoneEdt = v.findViewById(R.id.login_password_phone_edt);
        passwordEdt = v.findViewById(R.id.login_password_password_edt);
        performBtn = v.findViewById(R.id.login_password_perform_btn);
        captchaTxv = v.findViewById(R.id.login_password_captcha_txv);
    }

    @Override
    public void onShow() {

    }
}
