package cc.seedland.inf.passport.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import cc.seedland.inf.passport.PassportHome;
import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.base.BaseActivity;
import cc.seedland.inf.passport.base.IBaseView;
import cc.seedland.inf.passport.widget.PasswordEditText;

/**
 * Created by xuchunlei on 2017/11/8.
 */

public class LoginPasswordActivity extends BaseActivity<LoginPasswordPresenter> implements View.OnClickListener, IBaseView {

    private EditText phoneEdt;
    private PasswordEditText passwordEdt;

    @Override
    protected LoginPasswordPresenter createPresenter() {
        return new LoginPasswordPresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_password);

        findViewById(R.id.login_register_txv).setOnClickListener(this);
        findViewById(R.id.login_forget_password_txv).setOnClickListener(this);
        findViewById(R.id.login_password_captcha_txv).setOnClickListener(this);
        findViewById(R.id.login_password_perform_btn).setOnClickListener(this);

        phoneEdt = findViewById(R.id.login_password_phone_edt);
        passwordEdt = findViewById(R.id.login_password_password_edt);

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
        }else if(id == R.id.login_password_perform_btn) {
            String phone = phoneEdt.getText().toString();
            String password = passwordEdt.getText().toString();
            presenter.perform(phone, password);
        }
    }
}
