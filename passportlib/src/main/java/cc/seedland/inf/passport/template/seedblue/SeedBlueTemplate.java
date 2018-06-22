package cc.seedland.inf.passport.template.seedblue;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.login.LoginCaptchaActivity;
import cc.seedland.inf.passport.login.LoginCaptchaFragment;
import cc.seedland.inf.passport.login.LoginPasswordActivity;
import cc.seedland.inf.passport.login.LoginPasswordFragment;
import cc.seedland.inf.passport.password.ModifyPasswordActivity;
import cc.seedland.inf.passport.password.ModifyPasswordFragment;
import cc.seedland.inf.passport.password.ResetPasswordActivity;
import cc.seedland.inf.passport.password.ResetPasswordFragment;
import cc.seedland.inf.passport.register.RegisterActivity;
import cc.seedland.inf.passport.register.RegisterFragment;
import cc.seedland.inf.passport.template.ITemplate;
import cc.seedland.inf.passport.template.IViewAgent;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/21 10:38
 * 描述 ：
 **/
public class SeedBlueTemplate implements ITemplate {

    @Override
    public <T extends IViewAgent> T createAgent(String clzName) {
        if(LoginPasswordFragment.class.getName().equalsIgnoreCase(clzName)) {
            return (T)new SeedBlueLoginMainAgent();
        }else if(LoginCaptchaFragment.class.getName().equalsIgnoreCase(clzName)) {
            return (T)new SeedBlueLoginCaptchaAgent();
        }else if(RegisterFragment.class.getName().equalsIgnoreCase(clzName)) {
            return (T)new SeedBlueRegisterAgent();
        }else if(ResetPasswordFragment.class.getName().equalsIgnoreCase(clzName)) {
            return (T)new SeedBlueResetPasswordAgent();
        }else if(ModifyPasswordFragment.class.getName().equalsIgnoreCase(clzName)) {
            return (T)new SeedBlueModifyPasswordAgent();
        }
        return null;
    }

    @Override
    public int createLayout(String clzName) {
        return R.layout.template_seedblue;
    }

    @Override
    public void initView(final AppCompatActivity activity) {

        // 初始化标题栏
        Toolbar toolbar = activity.findViewById(R.id.template_toolbar);
        toolbar.setTitle("");
        activity.setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.setResult(activity.RESULT_CANCELED);
                activity.finish();
            }
        });
        TextView titleTxv = activity.findViewById(R.id.template_title_txv);

        // 加载界面
        Fragment fragment = null;
        String clzName = activity.getClass().getName();
        if (LoginPasswordActivity.class.getName().equalsIgnoreCase(clzName)) {
            fragment = Fragment.instantiate(activity, LoginPasswordFragment.class.getName());
            // 不显示分割线
            LinearLayoutCompat parent = (LinearLayoutCompat) toolbar.getParent();
            parent.setShowDividers(LinearLayoutCompat.SHOW_DIVIDER_NONE);
        }else if(LoginCaptchaActivity.class.getName().equalsIgnoreCase(clzName)) {
            fragment = Fragment.instantiate(activity, LoginCaptchaFragment.class.getName());
            titleTxv.setText(R.string.login_captcha_title);
        }else if(RegisterActivity.class.getName().equalsIgnoreCase(clzName)) {
            fragment = Fragment.instantiate(activity, RegisterFragment.class.getName());
            titleTxv.setText(R.string.register_title);
        }else if(ResetPasswordActivity.class.getName().equalsIgnoreCase(clzName)) {
            fragment = Fragment.instantiate(activity, ResetPasswordFragment.class.getName());
            titleTxv.setText(R.string.reset_password_title);
        }else if(ModifyPasswordActivity.class.getName().equalsIgnoreCase(clzName)) {
            fragment = Fragment.instantiate(activity, ModifyPasswordFragment.class.getName());
            titleTxv.setText(R.string.password_modify_title);
        }

        if(fragment != null) {
            FragmentManager manager = activity.getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.template_content, fragment);
            transaction.commitNowAllowingStateLoss();
        }
    }

}
