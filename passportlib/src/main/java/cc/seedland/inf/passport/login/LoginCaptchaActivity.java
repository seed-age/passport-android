package cc.seedland.inf.passport.login;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.base.PassportActivity;
import cc.seedland.inf.passport.common.ICaptchaView;
import cc.seedland.inf.passport.network.RuntimeCache;
import cc.seedland.inf.passport.widget.CountDownButton;

/**
 * Created by xuchunlei on 2017/11/16.
 */

public class LoginCaptchaActivity extends PassportActivity<LoginCaptchaPresenter> implements View.OnClickListener, ICaptchaView {

    private CountDownButton gainTxv;
    private EditText phoneEdt;
    private EditText captchaEdt;
    private EditText imgCaptchaEdt;
    private ImageView captchaImv;

    private String imgCaptchaId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        phoneEdt = findViewById(R.id.login_captcha_phone_edt);
        imgCaptchaEdt = findViewById(R.id.login_captcha_image_edt);
        captchaEdt = findViewById(R.id.login_captcha_captcha_edt);
        gainTxv = findViewById(R.id.login_captcha_gain_txv);
        gainTxv.setOnClickListener(this);
        findViewById(R.id.login_captcha_perform_btn).setOnClickListener(this);
        captchaImv = findViewById(R.id.login_captcha_image_imv);
        captchaImv.setOnClickListener(this);

        String phone = RuntimeCache.getPhone();
        phoneEdt.setText(phone);

        setTitle(getString(R.string.login_captcha_title));
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        presenter.performImageCaptcha();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_captcha;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String phone = phoneEdt.getText().toString();
        if(id == R.id.login_captcha_gain_txv) {
            String imgCaptcha = imgCaptchaEdt.getText().toString();
            presenter.performCaptcha(phone, imgCaptcha, imgCaptchaId);
        }else if(id == R.id.login_captcha_perform_btn) {
            String captcha = captchaEdt.getText().toString();
            presenter.perform(phone, captcha);
        }else if(id == R.id.login_captcha_image_imv) {
            presenter.performImageCaptcha();
        }
    }

    @Override
    protected LoginCaptchaPresenter createPresenter() {
        return new LoginCaptchaPresenter(this);
    }

    @Override
    public void startWaitingCaptcha() {
        gainTxv.startCountDown(true);
    }

    @Override
    public void updateImageCaptcha(Bitmap code, String captchaId) {
        captchaImv.setImageBitmap(code);
        this.imgCaptchaId = captchaId;
    }
}
