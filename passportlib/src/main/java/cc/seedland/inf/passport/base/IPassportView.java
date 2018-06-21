package cc.seedland.inf.passport.base;

import android.os.Bundle;

import cc.seedland.inf.corework.mvp.IBaseView;

/**
 * View基础接口
 * <p>
 *     目前只用于定义所有View的通用的方法，不用做其他View接口的父类，BaseActivity和BaseFragment实现此接口
 * </p>
 * Created by xuchunlei on 2017/11/8.
 */

public interface IPassportView extends IBaseView{


    /**
     * 显示提示，时间较长
     * @param tip
     */
    void showTip(String tip);

    /**
     * 关闭界面
     * 返回RESULT_OK
     */
    void close(Bundle args, String raw);
}
