package cc.seedland.inf.passport.password;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.base.PassportOActivity;
import cc.seedland.inf.passport.common.ICaptchaView;
import cc.seedland.inf.passport.network.RuntimeCache;
import cc.seedland.inf.passport.template.IViewAgent;
import cc.seedland.inf.passport.widget.CountDownButton;
import cc.seedland.inf.passport.widget.PasswordEditText;
import cc.seedland.inf.passport.widget.RatioImageView;

/**
 * Created by xuchunlei on 2017/11/16.
 */

public class ResetPasswordActivity extends PassportOActivity<IViewAgent, ResetPasswordPresenter> implements ICaptchaView, View.OnClickListener {

    private CountDownButton captchaBtn;
    private EditText phoneEdt;
    private EditText captchaEdt;
    private PasswordEditText passwordEdt;
    private PasswordEditText confirmEdt;
    private RatioImageView imgCaptchaImv;
    private EditText imgCaptchaEdt;

    private String imgCaptchaId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        phoneEdt = findViewById(R.id.password_reset_phone_edt);
        captchaBtn = findViewById(R.id.password_reset_captcha_txv);
        captchaBtn.setOnClickListener(this);
        captchaEdt = findViewById(R.id.password_reset_captcha_edt);
        passwordEdt = findViewById(R.id.password_reset_password_edt);
        confirmEdt = findViewById(R.id.password_reset_confirm_edt);
        findViewById(R.id.password_reset_perform_btn).setOnClickListener(this);
        imgCaptchaEdt = findViewById(R.id.password_reset_captcha_image_edt);
        imgCaptchaImv = findViewById(R.id.password_reset_captcha_image_imv);
        imgCaptchaImv.setOnClickListener(this);

        String phone = RuntimeCache.getPhone();
        phoneEdt.setText(phone);
        setTitle(getString(R.string.reset_password_title));

    }

    @Override
    protected ResetPasswordPresenter createPresenter() {
        return new ResetPasswordPresenter(this);
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
        if(id == R.id.password_reset_captcha_txv) {
            String imgCaptcha = imgCaptchaEdt.getText().toString();
            presenter.performCaptcha(phone, imgCaptcha, imgCaptchaId);
        }else if(id == R.id.password_reset_perform_btn) {
            String password = passwordEdt.getText().toString();
            String confirm = confirmEdt.getText().toString();
            String captcha = captchaEdt.getText().toString();
            presenter.performReset(phone, password, confirm, captcha);
        }else if(id == R.id.password_reset_captcha_image_imv) {
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
