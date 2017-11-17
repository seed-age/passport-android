package cc.seedland.inf.passport.base;

import java.lang.ref.WeakReference;

/**
 * Created by xuchunlei on 2017/11/16.
 */

public class BaseViewGuard {

    private BaseViewGuard() {

    }

    public static void callShowloadingSafely(WeakReference<? extends IBaseView> view) {
        IBaseView v = view.get();
        if(v != null) {
            v.showLoading();
        }
    }

    public static void callHideloadingSafely(WeakReference<? extends IBaseView> view) {
        IBaseView v = view.get();
        if(v != null) {
            v.hideLoading();
        }
    }

    public static void callShowErrorSafely(WeakReference<? extends IBaseView> view,String message) {
        IBaseView v = view.get();
        if(v != null) {
            v.showError(message);
        }
    }

    public static void callCloseSafely(WeakReference<? extends IBaseView> view) {
        IBaseView v = view.get();
        if(v != null) {
            v.close();
        }
    }
}
