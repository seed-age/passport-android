package cc.seedland.inf.passport.network;

import com.google.gson.annotations.SerializedName;

import cc.seedland.inf.passport.base.BaseBean;

/**
 * 数据包裹类
 * <p>
 *     封装非data数据
 * </p>
 * Created by xuchunlei on 2017/11/16.
 */

public class BeanWrapper {
    /** 返回码  */
    @SerializedName("error_code") public int code;

    /** 返回消息 */
    @SerializedName("error_message") public String message;

    // 时间戳
    private long timestamp;
    // 签名
    private String sign;

    /** Json格式的data字段 */
    public Object data;

    /**
     * 检验签名
     * @return
     */
    public boolean checkSign() {
//        return ApiUtil.checkSign(timestamp, sign);
        return true;
    }
}
