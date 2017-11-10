package cc.seedland.inf.samples;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import cc.seedland.inf.passport.PassportHome;
import cc.seedland.inf.passport.login.LoginActivity;
import cc.seedland.inf.passport.register.RegisterActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PassportHome.getInstance().init(getApplication());

        findViewById(R.id.main_login).setOnClickListener(this);
        findViewById(R.id.main_register).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_register:
//                Intent ir = new Intent(this, RegisterActivity.class);
//                startActivity(ir);
                PassportHome.getInstance().startRegister(this);
                break;
            case R.id.main_login:
//                Intent il = new Intent(this, LoginActivity.class);
//                startActivity(il);
                PassportHome.getInstance().startLogin(this);
                break;
        }
    }
}
