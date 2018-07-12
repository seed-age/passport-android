package cc.seedland.inf.passport.login;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.base.PassportFragment;
import cc.seedland.inf.passport.network.RuntimeCache;
import cc.seedland.inf.passport.password.ResetPasswordActivity;
import cc.seedland.inf.passport.register.RegisterActivity;
import cc.seedland.inf.passport.stat.PassportStatAgent;
import cc.seedland.inf.passport.template.LoginMainViewAgent;
import cc.seedland.inf.passport.util.Constant;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/22 09:32
 * 描述 ：
 **/
public class LoginPasswordFragment extends PassportFragment<LoginMainViewAgent, LoginPasswordPresenter> implements ILoginMainView, View.OnClickListener {

    private static final int REQUEST_CODE_REGISTER = 1;
    private static final int REQUEST_CODE_LOGIN_CAPTCHA = 2;
    private static final int REQUEST_CODE_RESET_PASSWORD = 3;



    @Override
    protected LoginPasswordPresenter createPresenter() {
        return new LoginPasswordPresenter(this);
    }

    @Override
    protected void initViews(View v) {
        super.initViews(v);

        v.findViewById(R.id.login_register_txv).setOnClickListener(this);
        v.findViewById(R.id.login_forget_password_txv).setOnClickListener(this);
        if(agent.captchaTxv != null) {
            agent.captchaTxv.setOnClickListener(this);
        }
        agent.performBtn.setOnClickListener(this);

        // 来自修改密码界面的手机号
        String phone = getActivity().getIntent().getStringExtra(Constant.EXTRA_KEY_PHONE);
        if(TextUtils.isEmpty(phone)) {
            phone = RuntimeCache.getPhone();
        }
        loadPhone(phone);
    }

    @Override
    public void loadPhone(String phone) {
        if(isAdded()) {
            agent.phoneEdt.setText(phone);
        }
    }

    @Override
    public void onClick(View v) {
        if(isAdded()) {
            int id = v.getId();
            if(id == R.id.login_register_txv) {
                PassportStatAgent.get().onRegisterEvent();
                startActivityForResult(createIntent(RegisterActivity.class.getName()), REQUEST_CODE_REGISTER);
            }else if(id == R.id.login_forget_password_txv) {
                PassportStatAgent.get().onPasswordResetEvent();
                startActivityForResult(createIntent(ResetPasswordActivity.class.getName()), REQUEST_CODE_RESET_PASSWORD);
            }else if(id == R.id.login_password_captcha_txv) {
                startActivityForResult(createIntent(LoginCaptchaActivity.class.getName()), REQUEST_CODE_LOGIN_CAPTCHA);
            }else if(id == R.id.login_password_perform_btn) {
                String phone = agent.phoneEdt.getText().toString();
                String password = agent.passwordEdt.getText().toString();
                presenter.perform(phone, password);
                PassportStatAgent.get().onLoginPasswordEvent();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(isAdded()) {
            if(resultCode == Activity.RESULT_OK) {
                Bundle args = data.getBundleExtra(Constant.EXTRA_KEY_RESULT);
                switch (requestCode) {
                    case REQUEST_CODE_REGISTER:
//                        presenter.refreshPhone(args.getString(Constant.EXTRA_KEY_PHONE), getString(R.string.register_success_tip));
//                        break;
                    case REQUEST_CODE_LOGIN_CAPTCHA:
                        close(data.getBundleExtra(Constant.EXTRA_KEY_RESULT), data.getStringExtra(Constant.EXTRA_KEY_RAW_RESULT));
                        break;
                    case REQUEST_CODE_RESET_PASSWORD:
                        presenter.refreshPhone(args.getString(Constant.EXTRA_KEY_PHONE), getString(R.string.reset_passpord_success_tip));
                        break;
                }
            }
        }
    }

    private Intent createIntent(String clzName) {
        Intent i = new Intent();
        i.setClassName(Constant.APP.getPackageName(), clzName);

        return i;
    }
}
