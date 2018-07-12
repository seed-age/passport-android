package cc.seedland.inf.passport.template;

import android.support.annotation.LayoutRes;
import android.support.annotation.StyleRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Set;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/21 10:38
 * 描述 ：
 **/
public interface ITemplate {

    @StyleRes int createTheme();
    /**
     * 创建界面代理
     * @param clzName
     * @param <T>
     * @return
     */
    <T extends IViewAgent> T createAgent(String clzName);

    /**
     * 创建界面布局
     * @param clzName
     * @return
     */
    @LayoutRes int createLayout(String clzName);

    /**
     * 初始化模版视图
     * @param activity
     */
    void initView(AppCompatActivity activity);

}
