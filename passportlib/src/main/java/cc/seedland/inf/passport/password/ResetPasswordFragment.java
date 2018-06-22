package cc.seedland.inf.passport.password;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.base.PassportFragment;
import cc.seedland.inf.passport.common.ICaptchaView;
import cc.seedland.inf.passport.network.RuntimeCache;
import cc.seedland.inf.passport.template.ResetPasswordViewAgent;
import cc.seedland.inf.passport.widget.CountDownButton;
import cc.seedland.inf.passport.widget.PasswordEditText;
import cc.seedland.inf.passport.widget.RatioImageView;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/22 17:05
 * 描述 ：
 **/
public class ResetPasswordFragment extends PassportFragment<ResetPasswordViewAgent, ResetPasswordPresenter> implements ICaptchaView, View.OnClickListener {
    private CountDownButton captchaBtn;
    private EditText phoneEdt;
    private EditText captchaEdt;
    private PasswordEditText passwordEdt;
    private PasswordEditText confirmEdt;
    private RatioImageView imgCaptchaImv;
    private EditText imgCaptchaEdt;

    private String imgCaptchaId;

    @Override
    protected void initViews(View v) {
        super.initViews(v);

        phoneEdt = v.findViewById(R.id.password_reset_phone_edt);
        captchaBtn = v.findViewById(R.id.password_reset_captcha_txv);
        captchaBtn.setOnClickListener(this);
        captchaEdt = v.findViewById(R.id.password_reset_captcha_edt);
        passwordEdt = v.findViewById(R.id.password_reset_password_edt);
        confirmEdt = v.findViewById(R.id.password_reset_confirm_edt);
        v.findViewById(R.id.password_reset_perform_btn).setOnClickListener(this);
        imgCaptchaEdt = v.findViewById(R.id.password_reset_captcha_image_edt);
        imgCaptchaImv = v.findViewById(R.id.password_reset_captcha_image_imv);
        imgCaptchaImv.setOnClickListener(this);

        String phone = RuntimeCache.getPhone();
        phoneEdt.setText(phone);
    }

    @Override
    protected ResetPasswordPresenter createPresenter() {
        return new ResetPasswordPresenter(this);
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
