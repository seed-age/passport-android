package cc.seedland.inf.passport.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cc.seedland.inf.passport.template.ITemplate;
import cc.seedland.inf.passport.template.TemplateFactory;

/**
 * 活动基类
 * Created by xuchunlei on 2017/11/8.
 */

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
