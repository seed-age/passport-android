package cc.seedland.inf.passport.network;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/12 09:16
 * 描述 ：
 **/
public interface TokenCallback  {

    /**
     * 接收到新token
     * @param token
     */
    void onTokenReceived(String token);

    /**
     * token过期
     * @param oldToken
     */
    void onTokenExpired(String oldToken);
}
