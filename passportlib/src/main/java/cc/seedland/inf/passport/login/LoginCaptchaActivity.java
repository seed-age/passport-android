package cc.seedland.inf.passport.login;

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

/**
 * Created by xuchunlei on 2017/11/16.
 */

public class LoginCaptchaActivity extends BaseActivity<LoginCaptchaPresenter> implements View.OnClickListener, ICaptchaView {

    private CountDownButton gainTxv;
    private EditText phoneEdt;
    private EditText captchaEdt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_captcha);

        phoneEdt = findViewById(R.id.login_captcha_phone_edt);
        captchaEdt = findViewById(R.id.login_captcha_captcha_edt);
        gainTxv = findViewById(R.id.login_captcha_gain_txv);
        gainTxv.setOnClickListener(this);
        findViewById(R.id.login_captcha_perform_btn).setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.login_captcha_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        TextView titleTxv = findViewById(R.id.login_captcha_title_txv);
        titleTxv.setText(R.string.login_captcha_title);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String phone = phoneEdt.getText().toString();
        if(id == R.id.login_captcha_gain_txv) {
            presenter.performCaptcha(phone);
        }else if(id == R.id.login_captcha_perform_btn) {
            String captcha = captchaEdt.getText().toString();
            presenter.perform(phone, captcha);
        }
    }

    @Override
    protected LoginCaptchaPresenter createPresenter() {
        return new LoginCaptchaPresenter();
    }

    @Override
    public void startWaitingCaptcha() {
        gainTxv.startCountDown(true);
    }
}
