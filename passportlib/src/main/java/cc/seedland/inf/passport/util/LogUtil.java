package cc.seedland.inf.passport.util;

import android.util.Log;

import cc.seedland.inf.passport.BuildConfig;

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
        if(BuildConfig.API_ENV) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if(BuildConfig.API_ENV) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if(BuildConfig.API_ENV) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if(BuildConfig.API_ENV) {
            Log.e(tag, msg);
        }
    }
}
