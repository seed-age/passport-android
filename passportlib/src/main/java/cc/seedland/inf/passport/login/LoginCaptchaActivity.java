package cc.seedland.inf.passport.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.base.BaseActivity;

/**
 * Created by xuchunlei on 2017/11/16.
 */

public class LoginCaptchaActivity extends BaseActivity<LoginPresenterImpl> implements ILoginView, View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_captcha);

        Toolbar toolbar = findViewById(R.id.login_captcha_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        TextView titleTxv = findViewById(R.id.login_captcha_title_txv);
        titleTxv.setText(R.string.login_captcha_title);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected LoginPresenterImpl createPresenter() {
        return new LoginPresenterImpl();
    }
}
