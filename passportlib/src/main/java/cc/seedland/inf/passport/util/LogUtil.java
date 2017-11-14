package cc.seedland.inf.passport.util;

import android.util.Log;

/**
 * 日志工具类
 *
 *
 * Created by xuchunlei on 2017/11/13.
 */

public class LogUtil {

    private LogUtil(){

    }

    public static void d(String tag, String msg) {
        if(Constant.DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if(Constant.DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if(Constant.DEBUG) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if(Constant.DEBUG) {
            Log.e(tag, msg);
        }
    }
}
