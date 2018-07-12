package cc.seedland.inf.passport.config;

import com.google.gson.annotations.SerializedName;

import cc.seedland.inf.network.BaseBean;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/29 17:10
 * 描述 ：
 **/
public class ChannelInfoBean extends BaseBean {

    /** 渠道号 */
    @SerializedName("channel")
    public String channel;

    /** 渠道名 */
    @SerializedName("channel_name")
    public String channelName;

    /** 用户协议 */
    @SerializedName("channel_protocol")
    public String channelProtocol;

    /** 渠道使用的验证码长度 */
    @SerializedName("sms_code_length")
    public int captchaLength;
}
