package cc.seedland.inf.passport.common;

import com.google.gson.annotations.SerializedName;

import cc.seedland.inf.network.BaseBean;

/**
 * Created by xuchunlei on 2017/11/14.
 */

public class TokenBean extends BaseBean {

    @SerializedName("refresh_sso_tk") public String token;
}
