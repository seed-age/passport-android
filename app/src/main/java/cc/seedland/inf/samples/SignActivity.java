package cc.seedland.inf.samples;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cc.seedland.inf.passport.network.ApiUtil;
import cc.seedland.inf.passport.util.DeviceUtil;

/**
 * Created by xuchunlei on 2017/11/21.
 */

public class SignActivity extends AppCompatActivity {

    private String sign;
    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        final EditText channel = findViewById(R.id.sign_channel);
        final EditText clientIp = findViewById(R.id.sign_client_ip);
        final EditText timestamp = findViewById(R.id.sign_timestamp);
        final EditText deviceType = findViewById(R.id.sign_device_type);
        final EditText deviceMac = findViewById(R.id.sign_device_mac);
        final EditText deviceImei = findViewById(R.id.sign_device_imei);
        final EditText key = findViewById(R.id.sign_key);
        final TextView result = findViewById(R.id.sign_result);

        clientIp.setText(DeviceUtil.getLocalIpAddress());
        deviceType.setText(Build.MANUFACTURER + "-" + Build.MODEL);
        deviceMac.setText(DeviceUtil.getMacAddress());
        deviceImei.setText(DeviceUtil.getDeviceId());

        final TreeMap<String, String> commonParams = new TreeMap<>();

        findViewById(R.id.sign_perform).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonParams.clear();
                obtain(commonParams, "channel", channel);
                obtain(commonParams, "timestamp", timestamp);
                obtain(commonParams, "client_ip", clientIp);
                obtain(commonParams,"device_type",deviceType);
//                obtain(commonParams, "device_mac", deviceMac);
//                obtain(commonParams, "device_imei", deviceImei);
                sign = ApiUtil.testSign(commonParams, key.getText().toString());
                result.setText("签名---->" + sign);
            }
        });

        final EditText urlEdt = findViewById(R.id.sign_url);
        final EditText phoneEdt = findViewById(R.id.sign_phone);
        final EditText passwordEdt = findViewById(R.id.sign_password);
        findViewById(R.id.sign_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String url = obtainText(urlEdt);
                String phone = obtainText(phoneEdt);
                String password = obtainText(passwordEdt);
                if(!TextUtils.isEmpty(url) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password)) {
                    final Map<String, String> params = new HashMap<>();
                    params.put("mobile", phone);
                    params.put("password", password);

                    ExecutorService service = Executors.newSingleThreadExecutor();
                    service.execute(new Runnable() {
                        @Override
                        public void run() {
                            Message msg = handler.obtainMessage();
                            msg.what = 1;
//                            msg.obj = ApiUtil.testPost(url, commonParams, params, sign);
                            handler.sendMessage(msg);

                        }
                    });

                }else {
                    Toast.makeText(SignActivity.this, "手机号码和密码不能为空", Toast.LENGTH_SHORT).show();
                }

            }
        });

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                result.setText(msg.obj.toString());
            }
        };

    }

    private void obtain(Map<String, String> params, String paramKey, EditText text) {
        if(!TextUtils.isEmpty(text.getText())) {
            String value = text.getText().toString().trim();
            if(!TextUtils.isEmpty(value)) {
                params.put(paramKey, value);
            }
        }
    }

    private String obtainText(EditText edit) {
        if (!TextUtils.isEmpty(edit.getText())) {
            String value = edit.getText().toString().trim();
            if (!TextUtils.isEmpty(value)) {
                return value;
            }
        }
        return null;
    }

}
