package cc.seedland.inf.passport.base;

import android.os.Bundle;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by xuchunlei on 2017/11/16.
 */

public class BaseViewGuard {

    private BaseViewGuard() {

    }

    public static void callShowloadingSafely(Reference<? extends IPassportView> view) {
        IPassportView v = view.get();
        if(v != null) {
            v.showLoading();
        }
    }

    public static void callHideloadingSafely(Reference<? extends IPassportView> view) {
        IPassportView v = view.get();
        if(v != null) {
            v.hideLoading();
        }
    }

    public static void callShowToastSafely(Reference<? extends IPassportView> view, String message) {
        IPassportView v = view.get();
        if(v != null) {
            v.showToast(message);
        }
    }

    public static void callShowTipSafely(Reference<? extends IPassportView> view, String message) {
        IPassportView v = view.get();
        if(v != null) {
            v.showTip(message);
        }
    }

    public static void callCloseSafely(Reference<? extends IPassportView> view, Bundle args, String raw) {
        IPassportView v = view.get();
        if(v != null) {
            v.close(args, raw);
        }
    }
}
