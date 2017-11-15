package cc.seedland.inf.passport.base;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xuchunlei on 2017/11/15.
 */

public class BaseBean {

    /** 返回码  */
    @SerializedName("error_code") public int code;

    /** 返回消息 */
    @SerializedName("error_message") public String message;
}
