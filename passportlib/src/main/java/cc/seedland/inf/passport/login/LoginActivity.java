package cc.seedland.inf.passport.login;

import android.os.Bundle;
import android.support.annotation.Nullable;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.base.BaseActivity;
import cc.seedland.inf.passport.base.BasePresenter;

/**
 * Created by xuchunlei on 2017/11/8.
 */

public class LoginActivity extends BaseActivity<LoginPresenterImpl> implements ILoginView {

    @Override
    protected LoginPresenterImpl createPresenter() {
        return new LoginPresenterImpl();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
