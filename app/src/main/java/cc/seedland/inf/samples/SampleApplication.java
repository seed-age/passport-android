package cc.seedland.inf.samples;

import android.app.Application;

import cc.seedland.inf.passport.PassportHome;

/**
 * Created by xuchunlei on 2017/11/10.
 */

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PassportHome.getInstance().init(this, getString(R.string.passport_channel), getString(R.string.passport_key));
    }
}
