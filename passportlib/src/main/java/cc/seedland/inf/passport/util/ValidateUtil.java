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
    public static int checkPhone(String phone) {
        if(checkNull(phone)) {
            return Constant.ERROR_CODE_PHONE_EMPTY;
        }

        if(!PHONE_NUMBER_REGEX.matcher(phone).matches()) {
            return Constant.ERROR_CODE_PHONE_FORMAT;
        }

        return Constant.ERROR_CODE_NONE;
    }

    /**
     * 检验验证码
     * @param captcha
     * @return
     */
    public static int checkCaptcha(String captcha) {
        if(checkNull(captcha)) {
            return Constant.ERROR_CODE_CAPTCHA_EMPTY;
        }

        return Constant.ERROR_CODE_NONE;
    }

    /**
     * 检验密码-用于确认
     * @param origin
     * @param confirm
     * @return
     */
    public static int checkPasswordConfirm(String origin, String confirm) {
        int errCode = checkPassword(origin);
        if(errCode == Constant.ERROR_CODE_NONE && !origin.equals(confirm)) {
            return Constant.ERROR_CODE_PASSWORD_CONFIRM;
        }
        return errCode;
    }

    /**
     * 检验密码
     * @param password
     * @return
     */
    public static int checkPassword(String password) {

        if(checkNull(password)) {
            return Constant.ERROR_CODE_PASSWORD_EMPTY;
        }

        if(password.length() < PASSWORD_MIN_LENGTH) {
            return Constant.ERROR_CODE_PASSWORD_FORMAT;
        }

        return Constant.ERROR_CODE_NONE;
    }
}
