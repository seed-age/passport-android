package cc.seedland.inf.passport;

import android.os.Bundle;

import com.lzy.okgo.model.HttpMethod;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.base.Request;

import cc.seedland.inf.passport.common.LoginBean;
import okhttp3.RequestBody;

/**
 * Created by xuchunlei on 2017/12/14.
 */

public class TestConstant {

    public static final String EMPTY_STRING = "";
    public static final String NULL_STRING = null;
    public static final String SPACE_STRING = "  ";

    // 设备相关
    public static final String DEVICE_IP_ADDRESS_V4 = "192.168.1.101";
    public static final String DEVICE_IP_ADDRESS_LOOPBACK = "127.0.0.1";
    public static final String DEVICE_IP_ADDRESS_V6 = "fec0::945e:edf5:749f:49c6";
    public static final String DEVICE_MAC_ADDRESS = "B0:55:09:E8:9A:CF";
    public static final String DEVICE_ANDROID_ID = "8850417ed04b75b1";
    public static final String DEVICE_ID_BY_ANDROID_ID = "ece793a5-85fa-3e6d-abd0-309e904a91b9";
    public static final String DEVICE_ID_BY_MAC_ADDRESS = "f86d8dee-8ca6-31c9-a427-09db107965da";

    // API相关
    public static final String CHANNEL = "channel-test";
    public static final String KEY = "aBcDEfghIjKLMnoPqRStuvWXYz0123456789";
    public static final String HOST_RIGHT_1 = "http://api.seedland.cc";
    public static final String HOST_RIGHT_2 = "http://test-api.seedland.cc";

    public static final String HOST_WRONG_1 = "api.seedland.cc";
    public static final String HOST_WRONG_2 = "api.seedland.cc/";

    public static final String API = HOST_RIGHT_1 + "/biz";
    public static final String API_2 = HOST_RIGHT_1 + "/biz2";

    // 电话号码
    public static final String PHONE_RIGHT_1 = "13800138000";
    public static final String PHONE_RIGHT_2 = "   13800139000    ";

    public static final String PHONE_WRONG_1 = "23800138000";
    public static final String PHONE_WRONG_2 = "1380013800";
    public static final String PHONE_WRONG_3 = "13800a13800";
    public static final String PHONE_WRONG_4 = "13800 13800";
    public static final String PHONE_WRONG_5 = "1380013800      ";

    // 密码
    public static final String PASSWORD_RIGHT_1 = "12345678";
    public static final String PASSWORD_RIGHT_2 = "    12345678   ";
    public static final String PASSWORD_RIGHT_3 = "  seed@  12345678   ";
    public static final String PASSWORD_WRONG_1 = "1234567";
    public static final String PASSWORD_WRONG_2 = "    1234567 ";

    // 验证码
    public static final String CAPTCHA_RIGHT_1 = "3375";
    public static final String CAPTCHA_RIGHT_2 = "   3375  ";

    // Request
    public static final String HEADER_CONTENT_TYPE_TEST = "application/x-www-form-urlencoded; charset=utf-8";

    // 登录相关

//    public static final String LOGIN_URL = "/1.0/login/password";

    public static final String LOGIN_RESPONSE_BODY_RIGHT =
            "{" +
                "\"error_code\":0," +
                "\"error_message\":\"succeed\"," +
                "\"data\":{" +
                    "\"uid\":100," +
                    "\"mobile\":\"13800138000\"," +
                    "\"nickname\":\"User_13800138000\"," +
                    "\"register_ip\":\"47.92.127.31\"," +
                    "\"sso_tk\":\"WVr5CyKwtEH1wAjXPG7AlNDZRb9FKvnAVhB8ppliGVnGpaTHryWoBgoiv56kMy1Nd9cC3N7ufASX7C3BoEXh/Wy8XYI7InM4e3RN6PUccHxejHy5LJiuk3cOrkDMyl22vO7tEu9UmKUJ5bTWkeOg2Nz+gpoeNA9u+rfPIRjh3gQ=\"," +
                    "\"register_time\":1511335462" +
                "}," +
                "\"sign\":\"eE6ah90HkjvOdSb2xJ9JFUS14kmJZ7DWh3PDytSNSXNHItYO3kVxj6hRg69mgdM0xxgyoRyPaBU7TWJkuoqyq0WYJ0nkU9GpVbwv0lJaTNKNlNRC0+C6V0rRRnI/j5/ABLEXJBv4drk8wiEfiSwbfDGNiXDvB0HHwSTbf9/cKMA=\"," +
                "\"timestamp\":1513221836" +
            "}\n";

    public static final String RESPONSE_TOKEN = "WVr5CyKwtEH1wAjXPG7AlNDZRb9FKvnAVhB8ppliGVnGpaTHryWoBgoiv56kMy1Nd9cC3N7ufASX7C3BoEXh/Wy8XYI7InM4e3RN6PUccHxejHy5LJiuk3cOrkDMyl22vO7tEu9UmKUJ5bTWkeOg2Nz+gpoeNA9u+rfPIRjh3gQ=";
    public static final int RESPONSE_UID = 100;
    public static final String RESPONSE_NICKNAME = "User_13800138000";
    public static final Bundle LOGIN_BEAN_ARGS = new Bundle();

    public static final Request<LoginBean, PostRequest<LoginBean>> LOGIN_REQUEST;
    public static final Response<LoginBean> LOGIN_RESPONSE;

    static {
        /*------------ login ------------*/

        LOGIN_REQUEST = new PostRequest<>("");

        LoginBean loginBean = new LoginBean();
        loginBean.mobile = TestConstant.PHONE_RIGHT_1;
        loginBean.uid = RESPONSE_UID;
        loginBean.nickname = RESPONSE_NICKNAME;
        loginBean.token = RESPONSE_TOKEN;
        loginBean.raw = LOGIN_RESPONSE_BODY_RIGHT;
        LOGIN_RESPONSE = new Response<>();
        LOGIN_RESPONSE.setBody(loginBean);
    }

}
