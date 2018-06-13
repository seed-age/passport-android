package cc.seedland.inf.samples;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import cc.seedland.inf.passport.PassportHome;
import cc.seedland.inf.passport.network.TokenCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final int REQUEST_CODE_LOGIN = 1;
    private static final int REQUEST_CODE_PASSWORD = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.main_login).setOnClickListener(this);
        findViewById(R.id.main_password).setOnClickListener(this);
        findViewById(R.id.main_sign).setOnClickListener(this);

        PassportHome.getInstance().checkLogin(new TokenCallback() {
            @Override
            public void onTokenReceived(String token) {
                Log.e("MainActivity", "received new token");
            }

            @Override
            public void onTokenExpired(String oldToken) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_password:
                PassportHome.getInstance().startModifyPassword(this, REQUEST_CODE_PASSWORD);
                break;
            case R.id.main_login:
                PassportHome.getInstance().startLoginByPassword(this, REQUEST_CODE_LOGIN);
                break;
            case R.id.main_sign:
                Intent iSign = new Intent(this, SignActivity.class);
                startActivity(iSign);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_LOGIN:
//                Log.e("xuchunlei", data.getStringExtra(Constant.EXTRA_KEY_RAW_RESULT));
//                Log.e("xuchunlei", data.getBundleExtra(Constant.EXTRA_KEY_RESULT).toString());
                break;
            case REQUEST_CODE_PASSWORD:
//                Log.e("xuchunlei", data.getStringExtra(Constant.EXTRA_KEY_RAW_RESULT));
//                Log.e("xuchunlei", data.getBundleExtra(Constant.EXTRA_KEY_RESULT).toString());
                break;
        }
    }
}
