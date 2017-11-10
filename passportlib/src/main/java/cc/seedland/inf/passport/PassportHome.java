package cc.seedland.inf.passport;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.lzy.okgo.OkGo;

import cc.seedland.inf.passport.login.LoginActivity;
import cc.seedland.inf.passport.register.RegisterActivity;

/**
 * Passport门面,这里统一提供对外调用接口
 * Created by xuchunlei on 2017/11/10.
 */

public final class PassportHome {

    private static final PassportHome INSTANCE = new PassportHome();
    private PassportHome(){

    }

    /**
     * 初始化方法
     * @param app
     */
    public void init(Application app) {
        OkGo.getInstance().init(app);
    }

    public final static PassportHome getInstance() {
        return INSTANCE;
    }

    /**
     * 打开注册界面
     * @param context
     */
    public void startRegister(Context context) {
        Intent i = new Intent(context, RegisterActivity.class);
        context.startActivity(i);
    }

    /**
     * 打开登录界面
     * @param context
     */
    public void startLogin(Context context) {
        Intent i = new Intent(context, LoginActivity.class);
        context.startActivity(i);
    }

}
