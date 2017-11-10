package cc.seedland.inf.passport.register;

import android.os.Bundle;
import android.support.annotation.Nullable;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.base.BaseActivity;

/**
 * Created by xuchunlei on 2017/11/8.
 */

public class RegisterActivity extends BaseActivity<RegisterPresenterImpl> implements IRegisterView{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    protected RegisterPresenterImpl createPresenter() {
        return new RegisterPresenterImpl();
    }
}
