package cc.seedland.inf.passport.config;

import org.json.JSONObject;

import cc.seedland.inf.network.BaseBean;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/29 17:10
 * 描述 ：
 **/
public class ChannelInfoBean extends BaseBean {

    /** 渠道号 */
    public String channel;

    /** 渠道名 */
    public String channelName;

    /** 用户协议 */
    public String channelProtocol;

    /** 渠道使用的验证码长度 */
    public int captchaLength;

    public static ChannelInfoBean fromString(String json) {
        ChannelInfoBean info = new ChannelInfoBean();
        try {
            JSONObject data = new JSONObject(json);
            info.channel = data.getString("channel");
            info.channelName = data.getString("channel_name");
            info.channelProtocol = data.getString("channel_protocol");
            info.captchaLength = data.getInt("sms_code_length");
        }catch (Exception e) {

        }
        return info;
    }
}
