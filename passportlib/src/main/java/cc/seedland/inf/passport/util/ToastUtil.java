package cc.seedland.inf.passport.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/07/06 13:30
 * 描述 ：
 **/
public class ToastUtil {

    private static Toast sToast;

    public static void show(final CharSequence text, final int duration) {
        cancel();
        View v = Toast.makeText(Constant.APP, text, duration).getView();
        sToast = new Toast(Constant.APP);
        sToast.setView(v);
        sToast.setText(text);
        sToast.setDuration(duration);
        sToast.show();
    }

    /**
     * Cancel the sToast.
     */
    public static void cancel() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }
}
