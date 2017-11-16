package cc.seedland.inf.passport.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import cc.seedland.inf.passport.PassportHome;
import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.base.BaseActivity;

/**
 * Created by xuchunlei on 2017/11/8.
 */

public class LoginPasswordActivity extends BaseActivity<LoginPresenterImpl> implements ILoginView, View.OnClickListener{

    @Override
    protected LoginPresenterImpl createPresenter() {
        return new LoginPresenterImpl();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_password);

        findViewById(R.id.login_register_txv).setOnClickListener(this);
        findViewById(R.id.login_forget_password_txv).setOnClickListener(this);
        findViewById(R.id.login_password_captcha_txv).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.login_register_txv) {
            PassportHome.getInstance().startRegister(this);
        }else if(id == R.id.login_forget_password_txv) {
            PassportHome.getInstance().startResetPassword(this);
        }else if(id == R.id.login_password_captcha_txv) {
            PassportHome.getInstance().startLoginByCaptcha(this);
        }
    }
}
