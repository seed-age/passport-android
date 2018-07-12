package cc.seedland.inf.passport.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import cc.seedland.inf.passport.util.Constant;
import cc.seedland.inf.passport.util.LogUtil;

/**
 * 运行时缓存
 * <p>
 *     缓存服务端获取的一些数据
 * </p>
 * Created by xuchunlei on 2017/11/14.
 */

public class RuntimeCache {

    private static final String CACHE_NAME = "passport_cache";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_CONFIG = "config";

    private RuntimeCache() {

    }

    /**
     * 保存Token到缓存
     * @param token
     */
    public static void saveToken(String token) {
        save(KEY_TOKEN, token);
        LogUtil.d(Constant.TAG, "update token to " + token);
    }

    /**
     * 保存phone到缓存
     * @param phone
     */
    public static void savePhone(String phone) {
        save(KEY_PHONE, phone);
        LogUtil.d(Constant.TAG, "update phone to " + phone);
    }

    /**
     * 从缓存获取Token
     * @return
     */
    public static String getToken() {
        return obtain(KEY_TOKEN, "");
    }

    /**
     * 从缓存获取phone
     * @return
     */
    public static String getPhone() {
        return obtain(KEY_PHONE, "");
    }

    public static void saveConfig(String config) {
        save(KEY_CONFIG, config);
    }

    public static String getConfig() {
        return obtain(KEY_CONFIG, "");
    }

    private static void save(String key, String value) {
        if(Constant.APP != null) {
            SharedPreferences pref = Constant.APP.getSharedPreferences(CACHE_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(key, value);
            editor.commit();
        }else {
            throw new NullPointerException("initialize CONTEXT before using it");
        }
    }

    private static String obtain(String key, String def) {
        if(Constant.APP != null) {
            SharedPreferences pref = Constant.APP.getSharedPreferences(CACHE_NAME, Context.MODE_PRIVATE);
            return pref.getString(key, def);
        }
        throw new NullPointerException("initialize CONTEXT before using it");
    }

}
