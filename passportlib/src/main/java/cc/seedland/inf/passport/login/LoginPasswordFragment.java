package cc.seedland.inf.passport.login;

import cc.seedland.inf.corework.mvp.BaseFragment;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/22 09:32
 * 描述 ：
 **/
public class LoginPasswordFragment extends BaseFragment<LoginPasswordPresenter> {

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected LoginPasswordPresenter createPresenter() {
        return null;
    }

    @Override
    public void showError(int code, String msg) {

    }
}
