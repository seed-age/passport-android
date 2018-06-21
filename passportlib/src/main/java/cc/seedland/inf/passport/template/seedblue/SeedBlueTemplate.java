package cc.seedland.inf.passport.template.seedblue;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.login.LoginPasswordFragment;
import cc.seedland.inf.passport.template.ITemplate;
import cc.seedland.inf.passport.template.IViewAgent;
import cc.seedland.inf.passport.template.LoginCaptchaViewAgent;
import cc.seedland.inf.passport.template.LoginMainViewAgent;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/21 10:38
 * 描述 ：
 **/
public class SeedBlueTemplate implements ITemplate {

    @Override
    public LoginMainViewAgent createLoginMainAgent() {
        return new SeedBlueLoginMainView();
    }

    @Override
    public LoginCaptchaViewAgent createLoginCaptchaAgent() {
        return new SeedBlueLoginCaptchaView();
    }

    @Override
    public <T extends IViewAgent> T createAgent(String clzName) {
        if(LoginPasswordFragment.class.getName().equalsIgnoreCase(clzName)) {
            return (T)new SeedBlueLoginMainView();
        }
        return null;
    }

    @Override
    public int createLayout(String clzName) {
        return R.layout.template_toolbar;
    }

    public static Toolbar initToolbar(View v) {
        final AppCompatActivity activity = (AppCompatActivity) v.getContext();
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
        return toolbar;
    }

    public static void initContent(View v, @LayoutRes int layout) {
        ViewStub contentStub = v.findViewById(R.id.template_content_stub);
        if(layout == 0) {
            throw new IllegalArgumentException("supply a valid layout resource please");
        }else {
            contentStub.setLayoutResource(layout);
            contentStub.inflate();
        }
    }

    public static final @LayoutRes int TEMPLATE_LAYOUT = R.layout.template_toolbar;

}
