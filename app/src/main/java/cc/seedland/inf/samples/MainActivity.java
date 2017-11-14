package cc.seedland.inf.samples;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import cc.seedland.inf.passport.PassportHome;
import cc.seedland.inf.passport.util.DeviceUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.main_login).setOnClickListener(this);
        findViewById(R.id.main_register).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_register:
                Log.e("xuchunlei", "manufacturer:" + Build.MANUFACTURER + "***Model:" + Build.MODEL);
                PassportHome.getInstance().startRegister(this);
                break;
            case R.id.main_login:
                PassportHome.getInstance().startLogin(this);
                break;
        }
    }
}
