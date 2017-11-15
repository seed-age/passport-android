package cc.seedland.inf.passport.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.base.BaseActivity;
import cc.seedland.inf.passport.widget.CountDownButton;

/**
 * Created by xuchunlei on 2017/11/8.
 */

public class RegisterActivity extends BaseActivity<RegisterPresenterImpl> implements IRegisterView, View.OnClickListener{

    private EditText phoneEdt;
    private CountDownButton captchaBtn;
    private EditText captchaEdt;
    private EditText passwordEdt;
    private EditText passwordConfirmEdt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViewById(R.id.register_captcha_txv).setOnClickListener(this);
        findViewById(R.id.register_perform_btn).setOnClickListener(this);
        phoneEdt = findViewById(R.id.register_phone_edt);
        captchaEdt = findViewById(R.id.register_captcha_edt);
        captchaBtn = findViewById(R.id.register_captcha_txv);
        passwordEdt = findViewById(R.id.register_password_edt);
        passwordConfirmEdt = findViewById(R.id.register_password_confirm_edt);
    }

    @Override
    protected RegisterPresenterImpl createPresenter() {
        return new RegisterPresenterImpl();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String phone = phoneEdt.getText().toString();
        if(id == R.id.register_captcha_txv) {
            presenter.performCaptcha(phone);

        }else if(id == R.id.register_perform_btn) {
            String captcha = captchaEdt.getText().toString();
            String password = passwordEdt.getText().toString();
            String confirmPassword = passwordConfirmEdt.getText().toString();

            presenter.performRegister(phone, captcha, password, confirmPassword);
        }
    }

    @Override
    public void startWaitingCaptcha() {
        captchaBtn.startCountDown(true);
    }
}
