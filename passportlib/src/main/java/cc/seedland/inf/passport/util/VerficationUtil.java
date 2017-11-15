package cc.seedland.inf.passport.util;

import android.text.TextUtils;

/**
 * Created by xuchunlei on 2017/11/15.
 */

public class VerficationUtil {

    private VerficationUtil(){

    }

    public static boolean checkNull(String value) {
        return TextUtils.isEmpty(value) || TextUtils.isEmpty(value.trim());

    }
}
