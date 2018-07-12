package cc.seedland.inf.passport.template.hachi;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import cc.seedland.inf.passport.stat.PassportStatAgent;
import cc.seedland.inf.passport.template.ITemplate;
import cc.seedland.inf.passport.template.IViewAgent;
import cc.seedland.inf.passport.template.def.DefaultModifyPasswordAgent;
import cc.seedland.inf.passport.util.StatusBarUtil;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/21 11:03
 * 描述 ：
 **/
public class HachiTemplate implements ITemplate {

    @Override
    public int createTheme() {
        return R.style.HachiTheme;
    }

    @Override
    public <T extends IViewAgent> T createAgent(String clzName) {
        if(LoginPasswordFragment.class.getName().equalsIgnoreCase(clzName)) {
            return (T)new HachiLoginMainAgent();
        }else if(LoginCaptchaFragment.class.getName().equalsIgnoreCase(clzName)) {
            return (T)new HachiLoginCaptchaAgent();
        }else if(RegisterFragment.class.getName().equalsIgnoreCase(clzName)) {
            return (T)new HachiRegisterAgent();
        }else if(ResetPasswordFragment.class.getName().equalsIgnoreCase(clzName)) {
            return (T)new HachiResetPasswordAgent();
        }else if(ModifyPasswordFragment.class.getName().equalsIgnoreCase(clzName)) {
            return (T)new DefaultModifyPasswordAgent();
        }
        return null;
    }

    @Override
    public int createLayout(String clzName) {
        if(LoginPasswordActivity.class.getName().equalsIgnoreCase(clzName) || LoginCaptchaActivity.class.getName().equalsIgnoreCase(clzName)) {
            return R.layout.template_toolbar_tab;
        }else {
            return R.layout.template_toolbar;
        }

    }

    @Override
    public void initView(final AppCompatActivity activity) {
        Resources resources = activity.getResources();
        @ColorInt int colorBg = activity.getResources().getColor(R.color.gray_hachi);
        // 初始化状态栏
        int alpha = 0;
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            alpha = 66;
        }
        StatusBarUtil.setColor(activity, colorBg, alpha);
        StatusBarUtil.setLightMode(activity);


        // 初始化标题栏
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
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
        ViewGroup.LayoutParams params = toolbar.getLayoutParams();
        int barHeight = resources.getDimensionPixelSize(R.dimen.bar_height);
        params.height = barHeight;

        // 标题文本
        TextView titleTxv = activity.findViewById(R.id.title_txv);
        float sizePx = resources.getDimensionPixelSize(R.dimen.text_size_big);
        float size = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, sizePx, resources.getDisplayMetrics());
        titleTxv.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        titleTxv.setTypeface(null, Typeface.BOLD);
        titleTxv.setTextColor(Color.BLACK);

        // 内容区域背景色
        View v = activity.findViewById(R.id.template_content);
        if(v != null) {
            v.setBackgroundColor(colorBg);
        }

        // 分割线样式
        LinearLayoutCompat parent = (LinearLayoutCompat) toolbar.getParent();
        parent.setDividerDrawable(activity.getResources().getDrawable(R.drawable.bg_shadow_line));
        parent.setDividerPadding(dp2px(4));

        // 加载界面
        String fragmentClzName = null;
        String clzName = activity.getClass().getName();
        if (LoginPasswordActivity.class.getName().equalsIgnoreCase(clzName)) {
            initLogin(activity, LoginPasswordFragment.class.getName(), barHeight);
            titleTxv.setText(R.string.login_title_hachi);
        }else if(LoginCaptchaActivity.class.getName().equalsIgnoreCase(clzName)) {
            initLogin(activity, LoginCaptchaFragment.class.getName(), barHeight);
            titleTxv.setText(R.string.login_title_hachi);
        }else if(RegisterActivity.class.getName().equalsIgnoreCase(clzName)) {
            fragmentClzName = RegisterFragment.class.getName();
            titleTxv.setText(R.string.register_title_hachi);
        }else if(ResetPasswordActivity.class.getName().equalsIgnoreCase(clzName)) {
            fragmentClzName = ResetPasswordFragment.class.getName();
            titleTxv.setText(R.string.reset_password_title_hachi);
        }else if(ModifyPasswordActivity.class.getName().equalsIgnoreCase(clzName)) {
            fragmentClzName = ModifyPasswordFragment.class.getName();
            titleTxv.setText(R.string.password_modify_title);
        }
        switchFragment(activity, fragmentClzName);

    }

    private void switchFragment(AppCompatActivity activity, String current, String previous) {
        if(current != null) {
            FragmentManager manager = activity.getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();

            Fragment previousF = manager.findFragmentByTag(previous);
            if(previousF != null) {
                transaction.hide(previousF);
            }

            Fragment currentF = manager.findFragmentByTag(current);
            if(currentF == null) {
                currentF = Fragment.instantiate(activity, current);
                transaction.add(R.id.template_content, currentF, current);
            }else {
                transaction.show(currentF);
            }
            transaction.commitNowAllowingStateLoss();
        }
    }

    private void switchFragment(AppCompatActivity activity, String clzName) {
        switchFragment(activity, clzName, null);
    }

    private void initLogin(final AppCompatActivity activity, final String clzName, int height) {
        // tab
        TabLayout tab = activity.findViewById(R.id.template_tab);

        // 高度
        ViewGroup.LayoutParams params = tab.getLayoutParams();
        params.height = height;

        // 添加分隔符
        LinearLayout linearLayout = (LinearLayout) tab.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
//        int[] attrs = { android.R.attr.listDivider };
//        TypedArray a = activity.getApplicationContext().obtainStyledAttributes(attrs);
//        Drawable divider = a.getDrawable(0);
//        a.recycle();
        Drawable divider = activity.getResources().getDrawable(R.drawable.divider_hachi);
        linearLayout.setDividerDrawable(divider);

        // 设置点击监听
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            private String previous;
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String current = tab.getTag().toString();
                switchFragment(activity, current, previous);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                previous = tab.getTag().toString();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        TabLayout.Tab passwordTab= tab.newTab().setText(R.string.login_tab_password);
        passwordTab.setTag(LoginPasswordFragment.class.getName());
        tab.addTab(passwordTab, true);

        TabLayout.Tab captchaTab = tab.newTab().setText(R.string.login_tab_captcha);
        captchaTab.setTag(LoginCaptchaFragment.class.getName());
        tab.addTab(captchaTab);

    }

    /**
     * dp 转换为 px
     * @param dpValue
     * @return
     */
    private static final int dp2px(int dpValue) {
        float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int)((dpValue * scale) + 0.5f);
    }
}
