package cc.seedland.inf.passport;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by xuchunlei on 2017/11/16.
 */

class PassportLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

    // 当前打开的活动计数
    private int count;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        count++;
        if(count == 1) {
            Toast.makeText(activity, "刷新Token", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        count--;
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
