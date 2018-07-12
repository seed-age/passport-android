package cc.seedland.inf.passport.web;

import cc.seedland.inf.corework.mvp.IBaseView;

/**
 * <pre>
 * 作者：徐春蕾
 * 联系方式：xuchunlei@seedland.cc / QQ:22003950
 * 时间：2018/03/16
 * 描述：
 * </pre>
 */

public interface WebContract {

    interface View extends IBaseView {
        void showPage(String url);
        void switchTitle(String title);
        void close(String cause);
    }

    interface Presenter {

        void loadPage(String rawUrl);
        void loadTitle(String webTitle, String defTitle);
        void handleError(int code, String msg);
    }
}
