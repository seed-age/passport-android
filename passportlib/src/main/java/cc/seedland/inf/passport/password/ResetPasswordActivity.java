package cc.seedland.inf.passport.password;

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
 * Created by xuchunlei on 2017/11/16.
 */

public class ResetPasswordActivity extends BaseActivity<ResetPasswordPresenter> implements ICaptchaView, View.OnClickListener {

    private CountDownButton captchaBtn;
    private EditText phoneEdt;
    private EditText captchaEdt;
    private PasswordEditText passwordEdt;
    private PasswordEditText confirmEdt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        phoneEdt = findViewById(R.id.password_reset_phone_edt);
        captchaBtn = findViewById(R.id.password_reset_captcha_txv);
        captchaBtn.setOnClickListener(this);
        captchaEdt = findViewById(R.id.password_reset_captcha_edt);
        passwordEdt = findViewById(R.id.password_reset_password_edt);
        confirmEdt = findViewById(R.id.password_reset_confirm_edt);
        findViewById(R.id.password_reset_perform_btn).setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.password_reset_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        TextView titleTxv = findViewById(R.id.password_reset_title_txv);
        titleTxv.setText(R.string.reset_password_title);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    protected ResetPasswordPresenter createPresenter() {
        return new ResetPasswordPresenter();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String phone = phoneEdt.getText().toString();
        if(id == R.id.password_reset_captcha_txv) {
            presenter.performCaptcha(phone);
        }else if(id == R.id.password_reset_perform_btn) {
            String password = passwordEdt.getText().toString();
            String confirm = confirmEdt.getText().toString();
            String captcha = captchaEdt.getText().toString();
            presenter.performReset(phone, password, confirm, captcha);
        }
    }

    @Override
    public void startWaitingCaptcha() {
        captchaBtn.startCountDown(true);
    }
}
