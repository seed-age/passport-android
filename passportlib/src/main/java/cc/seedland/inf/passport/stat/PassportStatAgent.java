package cc.seedland.inf.passport.stat;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/07/05 10:46
 * 描述 ：
 **/
public class PassportStatAgent {

    private static PassportStatAgent sInstance;

    private IStatEngine engine;
    private Map<String, String> params;

    private PassportStatAgent(IStatEngine engine, Map<String, String> params) {
        this.engine = engine;
        this.params = params;
    }

    public void onLoginPasswordEvent() {
        clickEvent("click_login_password");
    }

    public void onLoginCaptchaEvent() {
        clickEvent("click_login_captcha");
    }

    public void onRegisterEvent() {
        clickEvent("click_register");
    }

    public void onPasswordResetEvent() {
        clickEvent("click_password_reset");
    }

    public void onRegisterPerformEvent() {
        clickEvent("click_register_perform");
    }

    public void onPasswordModifyPerformEvent() {
        clickEvent("click_password_modify_perform");
    }

    public void onPasswordResetPerformEvent() {
        clickEvent("click_password_reset_perform");
    }

    public void onAgreementEvent(){
        clickEvent("click_agreement");
    }

    public void onLoginPasswordPage() {
        pageEvent("page_login_password");
    }

    public void onLoginCaptchaPage() {
        pageEvent("page_login_captcha");
    }

    public void onPasswordResetPage() {
        pageEvent("page_password_reset");
    }

    public void onPasswordModifyPage() {
        pageEvent("page_password_modify");
    }

    public void onRegisterPage() {
        pageEvent("page_register");
    }

    public void onHtmlPage(String url) {
        htmlEvent(url);
    }

    public static PassportStatAgent get() {
        if(sInstance == null) {
            sInstance = new PassportStatAgent(null, null);
        }
        return sInstance;
    }

    private void clickEvent(String key) {
        if(params != null && !params.isEmpty()) {
            String event = params.get(key);
            if(engine != null && !TextUtils.isEmpty(event)) {
                engine.onClickEvent(event);
            }
        }
    }

    private void pageEvent(String key) {
        if(params != null && !params.isEmpty()) {
            String page = params.get(key);
            if(engine != null && !TextUtils.isEmpty(page)) {
                engine.onPageEvent(page);
            }
        }
    }

    private void htmlEvent(String url) {
        if(engine != null && !TextUtils.isEmpty(url)) {
            engine.onHtmlEvent(url);
        }
    }

    public static class Builder {

        private IStatEngine engine;
        private Map<String, String> params = new HashMap<>();

        public Builder(IStatEngine engine) {
            this.engine = engine;
        }

        /**
         * 密码登录点击事件
         * @param eventId
         * @return
         */
        public Builder loginPassword(String eventId) {
            params.put("click_login_password", eventId);
            return this;
        }

        /**
         * 快捷登录点击事件
         * @param eventId
         * @return
         */
        public Builder loginCaptcha(String eventId) {
            params.put("click_login_captcha", eventId);
            return this;
        }

        public Builder register(String eventId) {
            params.put("click_register", eventId);
            return this;
        }

        public Builder passwordReset(String eventId) {
            params.put("click_password_reset", eventId);
            return this;
        }

        public Builder registerPerform(String eventId) {
            params.put("click_register_perform", eventId);
            return this;
        }

        public Builder passwordModifyPerform(String eventId) {
            params.put("click_password_modify_perform", eventId);
            return this;
        }

        public Builder passwordResetPerform(String eventId) {
            params.put("click_password_reset_perform", eventId);
            return this;
        }

        public Builder agreement(String eventId) {
            params.put("click_agreement", eventId);
            return this;
        }

        public Builder loginPasswordPage(String pageId) {
            params.put("page_login_password", pageId);
            return this;
        }

        public Builder loginCaptchaPage(String pageId) {
            params.put("page_login_captcha", pageId);
            return this;
        }

        public Builder passwordResetPage(String pageId) {
            params.put("page_password_reset", pageId);
            return this;
        }

        public Builder passwordModifyPage(String pageId) {
            params.put("page_password_modify", pageId);
            return this;
        }

        public Builder registerPage(String pageId) {
            params.put("page_register", pageId);
            return this;
        }

        public void build() {
            sInstance = new PassportStatAgent(engine, params);
        }
    }
}
