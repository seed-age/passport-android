package cc.seedland.inf.passport.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cc.seedland.inf.passport.template.ITemplate;
import cc.seedland.inf.passport.template.TemplateFactory;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/22 10:24
 * 描述 ：活动基类
 **/

public abstract class PassportActivity extends AppCompatActivity {

    protected ITemplate template = TemplateFactory.getTemplate();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(template.createTheme());
        setContentView(template.createLayout(getClass().getName()));
        template.initView(this);
    }

}
