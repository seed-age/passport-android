package cc.seedland.inf.passport.template;

import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.config.Config;
import cc.seedland.inf.passport.widget.CountDownButton;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/21 15:04
 * 描述 ：
 **/
public abstract class LoginCaptchaViewAgent implements IViewAgent {

    public EditText phoneEdt;
    public EditText captchaEdt;
    public EditText imgCaptchaEdt;
    public Button performBtn;
    public CountDownButton gainTxv;

    @Override
    public void initViews(View v) {
        phoneEdt = v.findViewById(R.id.login_captcha_phone_edt);
        imgCaptchaEdt = v.findViewById(R.id.captcha_image_edt);
        captchaEdt = v.findViewById(R.id.captcha_edt);
        performBtn = v.findViewById(R.id.login_captcha_perform_btn);
        gainTxv = v.findViewById(R.id.captcha_gain_txv);

        Config.get().configEdit(imgCaptchaEdt, captchaEdt);
    }

    @Override
    public void onShow() {

    }
}
