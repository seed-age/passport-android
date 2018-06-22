package cc.seedland.inf.passport.register;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.base.PassportActivity;
import cc.seedland.inf.passport.common.ICaptchaView;
import cc.seedland.inf.passport.widget.CountDownButton;
import cc.seedland.inf.passport.widget.PasswordEditText;
import cc.seedland.inf.passport.widget.RatioImageView;

/**
 * Created by xuchunlei on 2017/11/8.
 */

public class RegisterActivity extends PassportActivity<RegisterPresenter> implements ICaptchaView, View.OnClickListener{

    private EditText phoneEdt;
    private CountDownButton captchaBtn;
    private EditText captchaEdt;
    private PasswordEditText passwordEdt;
    private PasswordEditText passwordConfirmEdt;
    private RatioImageView imgCaptchaImv;
    private EditText imgCaptchaEdt;

    private String imgCaptchaId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.register_captcha_txv).setOnClickListener(this);
        findViewById(R.id.register_perform_btn).setOnClickListener(this);
        phoneEdt = findViewById(R.id.register_phone_edt);
        captchaEdt = findViewById(R.id.register_captcha_edt);
        captchaBtn = findViewById(R.id.register_captcha_txv);
        passwordEdt = findViewById(R.id.register_password_edt);
        passwordConfirmEdt = findViewById(R.id.register_password_confirm_edt);
        imgCaptchaEdt = findViewById(R.id.register_captcha_image_edt);
        imgCaptchaImv = findViewById(R.id.register_captcha_image_imv);
        imgCaptchaImv.setOnClickListener(this);

        setTitle(getString(R.string.register_title));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        presenter.performImageCaptcha();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String phone = phoneEdt.getText().toString();
        if(id == R.id.register_captcha_txv) {
            String imgCaptcha = imgCaptchaEdt.getText().toString();
            presenter.performCaptcha(phone, imgCaptcha, imgCaptchaId);

        }else if(id == R.id.register_perform_btn) {
            String captcha = captchaEdt.getText().toString();
            String password = passwordEdt.getText().toString();
            String confirmPassword = passwordConfirmEdt.getText().toString();

            presenter.performRegister(phone, captcha, password, confirmPassword);
        }else if(id == R.id.register_captcha_image_imv) {
            presenter.performImageCaptcha();
        }
    }

    @Override
    public void startWaitingCaptcha() {
        captchaBtn.startCountDown(true);
    }

    @Override
    public void updateImageCaptcha(Bitmap code, String captchaId) {
        this.imgCaptchaId = captchaId;
        imgCaptchaImv.setImageBitmap(code);
    }
}
