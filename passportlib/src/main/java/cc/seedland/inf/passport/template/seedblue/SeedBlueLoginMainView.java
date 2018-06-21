package cc.seedland.inf.passport.template.seedblue;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.template.LoginMainViewAgent;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/21 11:18
 * 描述 ：
 **/
public class SeedBlueLoginMainView extends LoginMainViewAgent {

    @Override
    public int layout() {
        return SeedBlueTemplate.TEMPLATE_LAYOUT;
    }

    @Override
    public void initViews(View v) {
        Toolbar toolbar = SeedBlueTemplate.initToolbar(v);
        SeedBlueTemplate.initContent(v, R.layout.activity_login_password);

        // 不显示分割线
        LinearLayoutCompat parent = (LinearLayoutCompat) toolbar.getParent();
        parent.setShowDividers(LinearLayoutCompat.SHOW_DIVIDER_NONE);
    }
}
