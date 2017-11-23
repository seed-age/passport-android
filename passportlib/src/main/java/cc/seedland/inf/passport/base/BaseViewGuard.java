package cc.seedland.inf.passport.base;

import android.os.Bundle;

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

    public static void callShowToastSafely(WeakReference<? extends IBaseView> view, String message) {
        IBaseView v = view.get();
        if(v != null) {
            v.showToast(message);
        }
    }

    public static void callShowTipSafely(WeakReference<? extends IBaseView> view, String message) {
        IBaseView v = view.get();
        if(v != null) {
            v.showTip(message);
        }
    }

    public static void callCloseSafely(WeakReference<? extends IBaseView> view, Bundle args, String raw) {
        IBaseView v = view.get();
        if(v != null) {
            v.close(args, raw);
        }
    }
}
