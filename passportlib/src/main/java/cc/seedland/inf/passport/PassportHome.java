package cc.seedland.inf.passport;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.lzy.okgo.OkGo;

import cc.seedland.inf.network.Networkit;
import cc.seedland.inf.passport.config.Config;
import cc.seedland.inf.passport.login.LoginCaptchaActivity;
import cc.seedland.inf.passport.login.LoginPasswordActivity;
import cc.seedland.inf.passport.network.ApiUtil;
import cc.seedland.inf.passport.network.RuntimeCache;
import cc.seedland.inf.passport.network.TokenCallback;
import cc.seedland.inf.passport.password.ModifyPasswordActivity;
import cc.seedland.inf.passport.password.ResetPasswordActivity;
import cc.seedland.inf.passport.register.RegisterActivity;
import cc.seedland.inf.passport.util.Constant;

/**
 * Passport门面,这里统一提供对外调用接口
 * Created by xuchunlei on 2017/11/10.
 */

public final class PassportHome {

    private static final PassportHome INSTANCE = new PassportHome();
    private static final PassportLifecycleCallbacks LIFECYCLE_CALLBACKS = new PassportLifecycleCallbacks();
    private PassportHome(){

    }

    /**
     * 初始化方法
     * <p>
     *     初始化PassportSDK开发环境
     * </p>
     * @param app Application实例
     * @param channel 渠道号
     * @param key 开发者key
     */
    @Deprecated
    public void init(Application app, String channel, String key) {
        Constant.APP = app;
        Networkit.init(app, channel, key);
        Config.update();
    }

    public static void init(Application app) {
        Constant.APP = app;
        String channel = app.getString(R.string.channel);
        String key = app.getString(R.string.key);
        Networkit.init(app, channel, key);
        Config.update();
    }

    public final static PassportHome getInstance() {
        return INSTANCE;
    }

    /**
     * 开启或关闭刷新token，默认关闭自动
     * 必须在初始化之后调用，才会生效
     * @param value
     */
    public final static void enableTokenUpdate(boolean value) {
        if(value) {
            if(Constant.APP != null) {
                Constant.APP.registerActivityLifecycleCallbacks(LIFECYCLE_CALLBACKS);
            }
        }else {
            if(Constant.APP != null) {
                Constant.APP.unregisterActivityLifecycleCallbacks(LIFECYCLE_CALLBACKS);
            }
        }
    }

    /**
     * 打开注册界面
     * @param context
     */
    public void startRegister(Context context, int requestCode) {
        start(context, RegisterActivity.class.getName(), requestCode);
    }

    /**
     * 打开密码登录界面
     * @param context
     */
    public void startLoginByPassword(final Context context, final int requestCode) {
        start(context, LoginPasswordActivity.class.getName(), requestCode);
    }

    /**
     * 打开验证码登录界面
     * @param context
     */
    public void startLoginByCaptcha(Context context, int requestCode) {
        start(context, LoginCaptchaActivity.class.getName(), requestCode);
    }

    /**
     * 打开重置密码界面
     * @param context
     */
    public void startResetPassword(Context context, int requestCode) {
        start(context, ResetPasswordActivity.class.getName(), requestCode);
    }

    /**
     * 打开修改密码界面
     * @param context
     */
    public void startModifyPassword(Context context, int requestCode) {
        start(context, ModifyPasswordActivity.class.getName(), requestCode);
    }

    /**
     * 登录状态
     * @return
     */
    public String getToken() {
        return RuntimeCache.getToken();
    }

    /**
     * 登出
     */
    public void logout() {
        RuntimeCache.saveToken("");

    }

    public void checkLogin(TokenCallback callback) {
        ApiUtil.refreshToken(callback);
    }

    /**
     * 是否在Passport内部调用
     * @param context
     * @return
     */
    private boolean isInner(Context context) {
        return context != null
                && context instanceof LoginPasswordActivity;
    }

    private void start(Context context, String clzName, int requestCode) {
        Intent i = new Intent();
        i.setClassName(Constant.APP.getPackageName(), clzName);

        if(context instanceof Activity) {
            ((Activity) context).startActivityForResult(i, requestCode);
        }else {
            context.startActivity(i);
        }
    }

}
