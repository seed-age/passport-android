package cc.seedland.inf.passport.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import cc.seedland.inf.passport.PassportHome;
import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.base.BaseActivity;
import cc.seedland.inf.passport.base.IBaseView;
import cc.seedland.inf.passport.util.Constant;
import cc.seedland.inf.passport.widget.PasswordEditText;

/**
 * Created by xuchunlei on 2017/11/8.
 */

public class LoginPasswordActivity extends BaseActivity<LoginPasswordPresenter> implements View.OnClickListener, IBaseView {

    private static final int REQUEST_CODE_REGISTER = 1;
    private static final int REQUEST_CODE_LOGIN_CAPTCHA = 2;
    private static final int REQUEST_CODE_RESET_PASSWORD = 3;

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
            PassportHome.getInstance().startRegister(this, REQUEST_CODE_REGISTER);
        }else if(id == R.id.login_forget_password_txv) {
            PassportHome.getInstance().startResetPassword(this, REQUEST_CODE_RESET_PASSWORD);
        }else if(id == R.id.login_password_captcha_txv) {
            PassportHome.getInstance().startLoginByCaptcha(this, REQUEST_CODE_LOGIN_CAPTCHA);
        }else if(id == R.id.login_password_perform_btn) {
            String phone = phoneEdt.getText().toString();
            String password = passwordEdt.getText().toString();
            presenter.perform(phone, password);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_REGISTER:
                Log.e("xuchunlei", data.getStringExtra(Constant.EXTRA_KEY_RAW_RESULT));
                Log.e("xuchunlei", data.getBundleExtra(Constant.EXTRA_KEY_RESULT).toString());
                break;
            case REQUEST_CODE_LOGIN_CAPTCHA:
                Log.e("xuchunlei", data.getStringExtra(Constant.EXTRA_KEY_RAW_RESULT));
                Log.e("xuchunlei", data.getBundleExtra(Constant.EXTRA_KEY_RESULT).toString());
                break;
            case REQUEST_CODE_RESET_PASSWORD:
//                Log.e("xuchunlei", data.getStringExtra(Constant.EXTRA_KEY_RAW_RESULT));
//                Log.e("xuchunlei", data.getBundleExtra(Constant.EXTRA_KEY_RESULT).toString());
                break;
        }
    }
}
