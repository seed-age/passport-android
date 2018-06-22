package cc.seedland.inf.passport.register;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.base.PassportFragment;
import cc.seedland.inf.passport.common.ICaptchaView;
import cc.seedland.inf.passport.template.RegisterViewAgent;
import cc.seedland.inf.passport.widget.CountDownButton;
import cc.seedland.inf.passport.widget.PasswordEditText;
import cc.seedland.inf.passport.widget.RatioImageView;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/22 16:51
 * 描述 ：
 **/
public class RegisterFragment extends PassportFragment<RegisterViewAgent, RegisterPresenter> implements ICaptchaView, View.OnClickListener {

    private EditText phoneEdt;
    private CountDownButton captchaBtn;
    private EditText captchaEdt;
    private PasswordEditText passwordEdt;
    private PasswordEditText passwordConfirmEdt;
    private RatioImageView imgCaptchaImv;
    private EditText imgCaptchaEdt;

    private String imgCaptchaId;

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    protected void initViews(View v) {
        super.initViews(v);

        v.findViewById(R.id.register_captcha_txv).setOnClickListener(this);
        v.findViewById(R.id.register_perform_btn).setOnClickListener(this);
        phoneEdt = v.findViewById(R.id.register_phone_edt);
        captchaEdt = v.findViewById(R.id.register_captcha_edt);
        captchaBtn = v.findViewById(R.id.register_captcha_txv);
        passwordEdt = v.findViewById(R.id.register_password_edt);
        passwordConfirmEdt = v.findViewById(R.id.register_password_confirm_edt);
        imgCaptchaEdt = v.findViewById(R.id.register_captcha_image_edt);
        imgCaptchaImv = v.findViewById(R.id.register_captcha_image_imv);
        imgCaptchaImv.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
