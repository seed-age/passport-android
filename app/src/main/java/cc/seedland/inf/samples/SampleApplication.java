package cc.seedland.inf.samples;

import android.app.Application;
import android.util.Log;

import cc.seedland.inf.passport.PassportHome;
import cc.seedland.inf.passport.stat.IStatEngine;
import cc.seedland.inf.passport.stat.PassportStatAgent;

/**
 * Created by xuchunlei on 2017/11/10.
 */

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        PassportHome.getInstance().init(this, getString(R.string.passport_channel), getString(R.string.passport_key));
        PassportHome.init(this);
        PassportHome.enableTokenUpdate(true);

        IStatEngine engine = new IStatEngine() {
            @Override
            public void onClickEvent(String eventId) {
                Log.e("stat", "click " + eventId + " launched");
            }

            @Override
            public void onPageEvent(String pageId) {
                Log.e("stat", "page " + pageId + " launched");
            }

            @Override
            public void onHtmlEvent(String pageUrl) {
                Log.e("stat", "html page " + pageUrl + " launched");
            }
        };
        PassportStatAgent.Builder builder = new PassportStatAgent.Builder(engine);
        builder.loginPassword("b050116")
                .loginCaptcha("b050117")
                .register("b050119")
                .passwordReset("b050120")
                .registerPerform("b050121")
                .passwordModifyPerform("b050055")
                .agreement("b050132")
                .loginPasswordPage("p020002")
                .loginCaptchaPage("p020051")
                .passwordResetPage("p020003")
                .passwordModifyPage("p020004")
                .registerPage("p020001");

        builder.build();
    }

}
