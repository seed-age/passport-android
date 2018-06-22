package cc.seedland.inf.passport.login;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.base.PassportFragment;
import cc.seedland.inf.passport.common.ICaptchaView;
import cc.seedland.inf.passport.network.RuntimeCache;
import cc.seedland.inf.passport.template.LoginCaptchaViewAgent;
import cc.seedland.inf.passport.widget.CountDownButton;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/22 15:51
 * 描述 ：
 **/
public class LoginCaptchaFragment extends PassportFragment<LoginCaptchaViewAgent, LoginCaptchaPresenter> implements ICaptchaView, View.OnClickListener {

    private CountDownButton gainTxv;
    private EditText phoneEdt;
    private EditText captchaEdt;
    private EditText imgCaptchaEdt;
    private ImageView captchaImv;

    private String imgCaptchaId;

    @Override
    protected LoginCaptchaPresenter createPresenter() {
        return new LoginCaptchaPresenter(this);
    }

    @Override
    protected void initViews(View v) {
        super.initViews(v);
        phoneEdt = v.findViewById(R.id.login_captcha_phone_edt);
        imgCaptchaEdt = v.findViewById(R.id.login_captcha_image_edt);
        captchaEdt = v.findViewById(R.id.login_captcha_captcha_edt);
        gainTxv = v.findViewById(R.id.login_captcha_gain_txv);
        gainTxv.setOnClickListener(this);
        v.findViewById(R.id.login_captcha_perform_btn).setOnClickListener(this);
        captchaImv = v.findViewById(R.id.login_captcha_image_imv);
        captchaImv.setOnClickListener(this);

        String phone = RuntimeCache.getPhone();
        phoneEdt.setText(phone);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.performImageCaptcha();
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

    @Override
    public void onClick(View v) {
        if(isAdded()) {
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

    }
}
