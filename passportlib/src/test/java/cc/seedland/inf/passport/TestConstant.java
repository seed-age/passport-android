package cc.seedland.inf.passport;

import android.os.Bundle;

import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.base.Request;

import cc.seedland.inf.passport.common.LoginBean;

/**
 * Created by xuchunlei on 2017/12/14.
 */

public class TestConstant {

    public static final String EMPTY_STRING = "";
    public static final String NULL_STRING = null;
    public static final String SPACE_STRING = "  ";

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

    public static final Response<LoginBean> LOGIN_RESPONSE;

    static {
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
