package cc.seedland.inf.passport.base;

import java.lang.ref.WeakReference;

/**
 * Presenter基类
 * <p>
 *     所有Presenter的父类
 * </p>
 * Created by xuchunlei on 2017/11/9.
 */

public class BasePresenter<V extends IBaseView> {

    protected WeakReference<V> view;

    public void attach(V view) {
        this.view = new WeakReference<>(view);
    }
}
