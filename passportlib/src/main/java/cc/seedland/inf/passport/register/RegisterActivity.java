package cc.seedland.inf.passport.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.base.BaseActivity;
import cc.seedland.inf.passport.common.ICaptchaView;
import cc.seedland.inf.passport.widget.CountDownButton;
import cc.seedland.inf.passport.widget.PasswordEditText;

/**
 * Created by xuchunlei on 2017/11/8.
 */

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements ICaptchaView, View.OnClickListener{

    private EditText phoneEdt;
    private CountDownButton captchaBtn;
    private EditText captchaEdt;
    private PasswordEditText passwordEdt;
    private PasswordEditText passwordConfirmEdt;

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

        Toolbar toolbar = findViewById(R.id.register_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        TextView titleTxv = findViewById(R.id.register_title_txv);
        titleTxv.setText(R.string.register_title);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
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
