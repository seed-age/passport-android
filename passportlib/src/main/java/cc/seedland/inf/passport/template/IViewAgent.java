package cc.seedland.inf.passport.template;

import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/21 14:58
 * 描述 ：
 **/
public interface IViewAgent {

    @LayoutRes int layout();
    void initViews(View v);
}
