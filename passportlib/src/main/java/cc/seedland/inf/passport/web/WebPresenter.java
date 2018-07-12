package cc.seedland.inf.passport.web;

import cc.seedland.inf.corework.mvp.BasePresenter;

/**
 * <pre>
 * 作者：徐春蕾
 * 联系方式：xuchunlei@seedland.cc / QQ:22003950
 * 时间：2018/03/16
 * 描述：
 * </pre>
 */

public class WebPresenter extends BasePresenter<WebContract.View> implements WebContract.Presenter {

    private static final String REGEX_PATTERN_TITLE = "[\\u4e00-\\u9fa5]+";

    public WebPresenter(WebContract.View view) {
        super(view);
    }


    @Override
    public void loadPage(String rawUrl) {
        getView().showPage(rawUrl);
    }

    @Override
    public void loadTitle(String webTitle, String defTitle) {
        if(isValidTitle(webTitle)) {
            getView().switchTitle(webTitle);
        }else if(isValidTitle(defTitle)){
            getView().switchTitle(defTitle);
        }else {
            getView().close(webTitle);
        }
    }

    @Override
    public void handleError(int code, String msg) {
        getView().close(msg);
    }

    private boolean isValidTitle(String title) {
        if(title != null && !title.isEmpty()){
//            return Pattern.matches(REGEX_PATTERN_TITLE, title);
            return true;
        }
        return false;
    }
}
