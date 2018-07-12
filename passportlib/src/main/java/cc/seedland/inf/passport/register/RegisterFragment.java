package cc.seedland.inf.passport.register;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.base.PassportFragment;
import cc.seedland.inf.passport.common.ICaptchaView;
import cc.seedland.inf.passport.stat.PassportStatAgent;
import cc.seedland.inf.passport.template.RegisterViewAgent;
import cc.seedland.inf.passport.web.WebActivity;
import cc.seedland.inf.passport.widget.CountDownButton;
import cc.seedland.inf.passport.widget.PasswordOEditText;
import cc.seedland.inf.passport.widget.RatioImageView;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/22 16:51
 * 描述 ：
 **/
public class RegisterFragment extends PassportFragment<RegisterViewAgent, RegisterPresenter> implements IRegisterView, View.OnClickListener {

    private EditText confirmEdt;
    private RatioImageView imgCaptchaImv;
    private TextView agreementTxv;

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

        confirmEdt = v.findViewById(R.id.register_password_confirm_edt);
        imgCaptchaImv = v.findViewById(R.id.captcha_image_imv);
        imgCaptchaImv.setOnClickListener(this);

        agreementTxv = v.findViewById(R.id.register_agreement_txv);
        initAgreement(agreementTxv);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.performImageCaptcha();
    }

    @Override
    public void onClick(View v) {
        if(!isAdded()) {
            return;
        }
        int id = v.getId();
        String phone = agent.phoneEdt.getText().toString();
        if(id == R.id.captcha_gain_txv) {
            String imgCaptcha = agent.imgCaptchaEdt.getText().toString();
            presenter.performCaptcha(phone, imgCaptcha, imgCaptchaId);

        }else if(id == R.id.register_perform_btn) {
            String captcha = agent.captchaEdt.getText().toString();
            String password = agent.passwordEdt.getText().toString();
            String confirmPassword = confirmEdt != null ? confirmEdt.getText().toString(): null;
            presenter.performRegister(phone, captcha, password, confirmPassword);
            PassportStatAgent.get().onRegisterPerformEvent();
        }else if(id == R.id.captcha_image_imv) {
            presenter.performImageCaptcha();
        }
    }

    @Override
    public void startWaitingCaptcha() {
        if(isAdded()) {
            agent.gainBtn.startCountDown(true);
        }
    }

    @Override
    public void updateImageCaptcha(Bitmap code, String captchaId) {
        if(isAdded()) {
            this.imgCaptchaId = captchaId;
            imgCaptchaImv.setImageBitmap(code);
        }

    }

    private void initAgreement(TextView t) {
        final SpannableStringBuilder style = new SpannableStringBuilder();

        //设置文字
        style.append(getString(R.string.register_declare_agreement));

        //设置部分文字点击事件
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                presenter.performAgreement();
                PassportStatAgent.get().onAgreementEvent();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                TypedValue typedValue = new TypedValue();

                TypedArray a = getContext().obtainStyledAttributes(typedValue.data, new int[] { R.attr.colorPrimary });
                int color = a.getColor(0, 0);
                a.recycle();
                ds.setColor(color);
                ds.setUnderlineText(false);
            }
        };
        style.setSpan(clickableSpan, 7, 11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        t.setText(style);

        //配置给TextView
        t.setMovementMethod(LinkMovementMethod.getInstance());
        t.setText(style);
    }

    @Override
    public void showAgreement(String url) {
        Intent i = new Intent(getContext(), WebActivity.class);
        i.putExtra(WebActivity.EXTRA_KEY_TITLE, "使用协议");
        i.putExtra(WebActivity.EXTRA_KEY_URL, url);
        startActivity(i);
    }
}
