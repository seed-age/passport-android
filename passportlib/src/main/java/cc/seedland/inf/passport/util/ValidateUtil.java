package cc.seedland.inf.passport.util;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * Created by xuchunlei on 2017/11/15.
 */

public class ValidateUtil {

    private static final int PASSWORD_MIN_LENGTH = 8;
    private static final Pattern PHONE_NUMBER_REGEX = Pattern.compile("^1\\d{10}$");

    private ValidateUtil(){

    }

    public static boolean checkNull(String value) {
        return TextUtils.isEmpty(value) || TextUtils.isEmpty(value.trim());

    }

    /**
     * 检验手机号
     * @param phone
     * @return
     */
    public static boolean checkPhone(String phone) {
        if(!checkNull(phone)) {
            return PHONE_NUMBER_REGEX.matcher(phone).matches();
        }
        return false;
    }

    /**
     * 检验密码-用于确认
     * @param origin
     * @param confirm
     * @return
     */
    public static boolean checkPasswordConfirm(String origin, String confirm) {
        if(!checkNull(origin) && origin.length() >= PASSWORD_MIN_LENGTH) {
            return origin.equals(confirm);
        }
        return false;
    }

    /**
     * 检验密码
     * @param password
     * @return
     */
    public static boolean checkPassword(String password) {
        if(!checkNull(password) && password.length() >= PASSWORD_MIN_LENGTH) {
            return true;
        }
        return false;
    }
}
