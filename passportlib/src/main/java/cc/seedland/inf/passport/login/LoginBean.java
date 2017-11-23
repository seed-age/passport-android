package cc.seedland.inf.passport.login;

import com.google.gson.annotations.SerializedName;

import cc.seedland.inf.passport.base.BaseBean;
import cc.seedland.inf.passport.common.SimpleBean;

/**
 * Created by xuchunlei on 2017/11/16.
 */

public class LoginBean extends SimpleBean {

    /** 登录token */
    @SerializedName("sso_tk") public String token;

}
