package cc.seedland.inf.passport.login;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.base.PassportFragment;
import cc.seedland.inf.passport.common.ICaptchaView;
import cc.seedland.inf.passport.network.RuntimeCache;
import cc.seedland.inf.passport.stat.PassportStatAgent;
import cc.seedland.inf.passport.template.LoginCaptchaViewAgent;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/22 15:51
 * 描述 ：
 **/
public class LoginCaptchaFragment extends PassportFragment<LoginCaptchaViewAgent, LoginCaptchaPresenter> implements ICaptchaView, View.OnClickListener {

    private ImageView captchaImv;

    private String imgCaptchaId;

    @Override
    protected LoginCaptchaPresenter createPresenter() {
        return new LoginCaptchaPresenter(this);
    }

    @Override
    protected void initViews(View v) {
        super.initViews(v);

        agent.gainTxv.setOnClickListener(this);
        captchaImv = v.findViewById(R.id.captcha_image_imv);
        captchaImv.setOnClickListener(this);
        agent.performBtn.setOnClickListener(this);

        String phone = RuntimeCache.getPhone();
        agent.phoneEdt.setText(phone);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.performImageCaptcha();
    }

    @Override
    public void startWaitingCaptcha() {
        if(isAdded()) {
            agent.gainTxv.startCountDown(true);
        }
    }

    @Override
    public void updateImageCaptcha(Bitmap code, String captchaId) {
        if(isAdded()) {
            captchaImv.setImageBitmap(code);
            this.imgCaptchaId = captchaId;
        }
    }

    @Override
    public void onClick(View v) {
        if(isAdded()) {
            int id = v.getId();
            String phone = agent.phoneEdt.getText().toString();
            if(id == R.id.captcha_gain_txv) {
                String imgCaptcha = agent.imgCaptchaEdt.getText().toString();
                presenter.performCaptcha(phone, imgCaptcha, imgCaptchaId);
            }else if(id == R.id.login_captcha_perform_btn) {
                String captcha = agent.captchaEdt.getText().toString();
                presenter.perform(phone, captcha);
                PassportStatAgent.get().onLoginCaptchaEvent();
            }else if(id == R.id.captcha_image_imv) {
                presenter.performImageCaptcha();
            }
        }

    }
}
