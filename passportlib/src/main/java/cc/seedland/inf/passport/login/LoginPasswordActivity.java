package cc.seedland.inf.passport.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import cc.seedland.inf.passport.PassportHome;
import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.base.BaseActivity;
import cc.seedland.inf.passport.network.RuntimeCache;
import cc.seedland.inf.passport.util.Constant;
import cc.seedland.inf.passport.widget.PasswordEditText;

/**
 * Created by xuchunlei on 2017/11/8.
 */

public class LoginPasswordActivity extends BaseActivity<LoginPasswordPresenter> implements View.OnClickListener, ILoginMainView {

    private static final int REQUEST_CODE_REGISTER = 1;
    private static final int REQUEST_CODE_LOGIN_CAPTCHA = 2;
    private static final int REQUEST_CODE_RESET_PASSWORD = 3;

    private EditText phoneEdt;
    private PasswordEditText passwordEdt;

    @Override
    protected LoginPasswordPresenter createPresenter() {
        return new LoginPasswordPresenter(new LoginModel());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.login_register_txv).setOnClickListener(this);
        findViewById(R.id.login_forget_password_txv).setOnClickListener(this);
        findViewById(R.id.login_password_captcha_txv).setOnClickListener(this);
        findViewById(R.id.login_password_perform_btn).setOnClickListener(this);

        phoneEdt = findViewById(R.id.login_password_phone_edt);
        passwordEdt = findViewById(R.id.login_password_password_edt);

        // 来自修改密码界面的手机号
        String phone = getIntent().getStringExtra(Constant.EXTRA_KEY_PHONE);
        if(TextUtils.isEmpty(phone)) {
            phone = RuntimeCache.getPhone();
        }
        loadPhone(phone);


        setToolbarDivider(false);

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_login_password;
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
            presenter.perform(phone.trim(), password.trim());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            Bundle args = data.getBundleExtra(Constant.EXTRA_KEY_RESULT);
            switch (requestCode) {
                case REQUEST_CODE_REGISTER:
                    presenter.refreshPhone(args.getString(Constant.EXTRA_KEY_PHONE), getString(R.string.register_success_tip));
                    break;
                case REQUEST_CODE_LOGIN_CAPTCHA:
                    close(data.getBundleExtra(Constant.EXTRA_KEY_RESULT), data.getStringExtra(Constant.EXTRA_KEY_RAW_RESULT));
                    break;
                case REQUEST_CODE_RESET_PASSWORD:
                    presenter.refreshPhone(args.getString(Constant.EXTRA_KEY_PHONE), getString(R.string.reset_passpord_success_tip));
                    break;
            }
        }
    }

    @Override
    public void loadPhone(String phone) {
        phoneEdt.setText(phone);
    }
}
