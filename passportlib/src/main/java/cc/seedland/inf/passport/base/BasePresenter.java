package cc.seedland.inf.passport.base;

/**
 * Presenter基类
 * <p>
 *     所有Presenter的父类
 * </p>
 * Created by xuchunlei on 2017/11/9.
 */

public abstract class BasePresenter<V> {

    private V view;

    public void attach(V view) {
        this.view = view;
    }
}
