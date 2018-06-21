package cc.seedland.inf.passport.template.seedblue;

import android.view.View;
import android.widget.TextView;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.template.LoginCaptchaViewAgent;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/21 15:07
 * 描述 ：
 **/
public class SeedBlueLoginCaptchaView extends LoginCaptchaViewAgent {

    @Override
    public int layout() {
        return SeedBlueTemplate.TEMPLATE_LAYOUT;
    }

    @Override
    public void initViews(View v) {
        SeedBlueTemplate.initToolbar(v);
        TextView titleTxv = v.findViewById(R.id.template_title_txv);
        SeedBlueTemplate.initContent(v, R.layout.activity_login_captcha);
        titleTxv.setText(R.string.login_captcha_title);
    }

}
