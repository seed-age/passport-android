package cc.seedland.inf.passport.template.def;

import android.content.Context;
import android.view.View;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.template.LoginMainViewAgent;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/21 11:18
 * 描述 ：
 **/
public class DefaultLoginMainAgent extends LoginMainViewAgent {

    @Override
    public int layout() {
        return R.layout.fragment_login_password_default;
    }

    @Override
    public void initViews(View v) {
        super.initViews(v);
        final Context context = v.getContext();
        if(context instanceof View.OnClickListener) {
            v.findViewById(R.id.login_password_captcha_txv).setOnClickListener((View.OnClickListener) context);
        }

    }
}
