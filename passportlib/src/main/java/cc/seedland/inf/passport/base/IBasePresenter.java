package cc.seedland.inf.passport.base;

/**
 * Presenter基类借口
 * <p>
 *     所有Presenter的父接口
 * </p>
 *
 * Created by xuchunlei on 2017/11/8.
 */

public interface IBasePresenter<V extends IBaseView> {

    /**
     *
     * @param view
     */
    void attach(V view);
}
