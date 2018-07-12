package cc.seedland.inf.passport.stat;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/07/05 10:38
 * 描述 ：
 **/
public interface IStatEngine {

    void onClickEvent(String eventId);

    void onPageEvent(String pageId);

    void onHtmlEvent(String pageUrl);
}
