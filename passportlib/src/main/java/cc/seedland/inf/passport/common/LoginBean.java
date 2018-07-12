package cc.seedland.inf.passport.common;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xuchunlei on 2017/11/16.
 */

public class LoginBean extends SimpleBean {

    /** 登录token */
    @SerializedName("sso_tk") public String token;
    /** 登录类型 */
    @SerializedName("login_type") public int type;

}
