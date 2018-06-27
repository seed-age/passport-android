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
import cc.seedland.inf.passport.widget.PasswordOEditText;
import cc.seedland.inf.passport.widget.RatioImageView;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/22 16:51
 * 描述 ：
 **/
public class RegisterFragment extends PassportFragment<RegisterViewAgent, RegisterPresenter> implements ICaptchaView, View.OnClickListener {

    private PasswordOEditText passwordConfirmEdt;
    private RatioImageView imgCaptchaImv;

    private String imgCaptchaId;

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    protected void initViews(View v) {
        super.initViews(v);

        v.findViewById(R.id.captcha_gain_txv).setOnClickListener(this);
        agent.performBtn.setOnClickListener(this);

        passwordConfirmEdt = v.findViewById(R.id.register_password_confirm_edt);
        imgCaptchaImv = v.findViewById(R.id.captcha_image_imv);
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
        String phone = agent.phoneEdt.getText().toString();
        if(id == R.id.register_captcha_txv) {
            String imgCaptcha = agent.imgCaptchaEdt.getText().toString();
            presenter.performCaptcha(phone, imgCaptcha, imgCaptchaId);

        }else if(id == R.id.register_perform_btn) {
            String captcha = agent.captchaEdt.getText().toString();
            String password = agent.passwordEdt.getText().toString();
            String confirmPassword = passwordConfirmEdt != null ? passwordConfirmEdt.getText().toString(): null;
            presenter.performRegister(phone, captcha, password, confirmPassword);
        }else if(id == R.id.register_captcha_image_imv) {
            presenter.performImageCaptcha();
        }
    }

    @Override
    public void startWaitingCaptcha() {
        agent.gainBtn.startCountDown(true);
    }

    @Override
    public void updateImageCaptcha(Bitmap code, String captchaId) {
        this.imgCaptchaId = captchaId;
        imgCaptchaImv.setImageBitmap(code);
    }
}
