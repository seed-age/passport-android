package cc.seedland.inf.passport.password;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import cc.seedland.inf.passport.PassportHome;
import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.base.PassportFragment;
import cc.seedland.inf.passport.common.ICaptchaView;
import cc.seedland.inf.passport.network.RuntimeCache;
import cc.seedland.inf.passport.stat.PassportStatAgent;
import cc.seedland.inf.passport.template.ResetPasswordViewAgent;
import cc.seedland.inf.passport.widget.CountDownButton;
import cc.seedland.inf.passport.widget.PasswordOEditText;
import cc.seedland.inf.passport.widget.RatioImageView;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/22 17:05
 * 描述 ：
 **/
public class ResetPasswordFragment extends PassportFragment<ResetPasswordViewAgent, ResetPasswordPresenter> implements ICaptchaView, View.OnClickListener {

    private EditText confirmEdt;
    private RatioImageView imgCaptchaImv;

    private String imgCaptchaId;

    @Override
    protected void initViews(View v) {
        super.initViews(v);

        agent.captchaBtn.setOnClickListener(this);
        confirmEdt = v.findViewById(R.id.password_reset_confirm_edt);
        agent.performBtn.setOnClickListener(this);
        imgCaptchaImv = v.findViewById(R.id.captcha_image_imv);
        imgCaptchaImv.setOnClickListener(this);

        String phone = RuntimeCache.getPhone();
        agent.phoneEdt.setText(phone);
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
        if(isAdded()) {
            int id = v.getId();
            String phone = agent.phoneEdt.getText().toString();
            if(id == R.id.captcha_gain_txv) {
                String imgCaptcha = agent.imgCaptchaEdt.getText().toString();
                presenter.performCaptcha(phone, imgCaptcha, imgCaptchaId);
            }else if(id == R.id.password_reset_perform_btn) {
                String password = agent.passwordEdt.getText().toString();
                String confirm = confirmEdt != null ? confirmEdt.getText().toString() : null;
                String captcha = agent.captchaEdt.getText().toString();
                presenter.performReset(phone, password, confirm, captcha);
                if(TextUtils.isEmpty(RuntimeCache.getToken())) {
                    PassportStatAgent.get().onPasswordResetPerformEvent();
                }else {
                    PassportStatAgent.get().onPasswordModifyPerformEvent();
                }
            }else if(id == R.id.captcha_image_imv) {
                presenter.performImageCaptcha();
            }
        }
    }

    @Override
    public void startWaitingCaptcha() {
        if(isAdded()) {
            agent.captchaBtn.startCountDown(true);
        }
    }

    @Override
    public void updateImageCaptcha(Bitmap code, String captchaId) {
        if(isAdded()) {
            this.imgCaptchaId = captchaId;
            imgCaptchaImv.setImageBitmap(code);
        }
    }
}
