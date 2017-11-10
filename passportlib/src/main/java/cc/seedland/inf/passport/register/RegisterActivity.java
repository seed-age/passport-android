package cc.seedland.inf.passport.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.base.BaseActivity;

/**
 * Created by xuchunlei on 2017/11/8.
 */

public class RegisterActivity extends BaseActivity<RegisterPresenterImpl> implements IRegisterView, View.OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViewById(R.id.register_captcha_txv).setOnClickListener(this);
    }

    @Override
    protected RegisterPresenterImpl createPresenter() {
        return new RegisterPresenterImpl();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.register_captcha_txv) {

        }else if(id == R.id.register_do_btn) {

        }
    }
}
