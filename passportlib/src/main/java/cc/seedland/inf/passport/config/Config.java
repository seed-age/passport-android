package cc.seedland.inf.passport.config;

import android.text.InputFilter;
import android.widget.EditText;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.json.JSONObject;

import cc.seedland.inf.network.JsonCallback;
import cc.seedland.inf.network.Networkit;
import cc.seedland.inf.passport.network.RuntimeCache;
import cc.seedland.inf.passport.util.Constant;
import cc.seedland.inf.passport.util.LogUtil;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/07/11 14:58
 * 描述 ：
 **/
public class Config {

    private static final Config INSTANCE = new Config();
    private static final ChannelInfoBean CHANNEL_INFO;
    private static boolean updated;

    static {
        String config = RuntimeCache.getConfig();
        if(!config.isEmpty()) {
            CHANNEL_INFO = ChannelInfoBean.fromString(config);
        } else {
            CHANNEL_INFO = new ChannelInfoBean();
            CHANNEL_INFO.captchaLength = 6;
        }
    }

    public static final void update() {
        if(!updated) {
            updateInner(null);
        }

    }

    public static final Config get() {
        return INSTANCE;
    }

    public void getAgreement(ConfigCallback callback) {
        if(CHANNEL_INFO.channelProtocol.isEmpty()) {
            updateInner(callback);
        }else {
            callback.onConfigReceived(CHANNEL_INFO);
        }
    }

    public void configEdit(EditText... edits) {
        InputFilter[] filters = new InputFilter[]{ new InputFilter.LengthFilter(Config.get().getCaptchaLength()) };
        for(EditText e : edits) {
            e.setFilters(filters);
        }
    }

    private int getCaptchaLength() {
        return CHANNEL_INFO.captchaLength != 0 ? CHANNEL_INFO.captchaLength : 6;
    }

    private static void updateInner(final ConfigCallback callback) {
        OkGo.<JSONObject>get(Networkit.generateFullUrl(Constant.API_URL_CHANNEL_INFO))
                .execute(new JsonCallback(){
                    @Override
                    public void onSuccess(Response<JSONObject> response) {
                        JSONObject info = response.body();

                        try {
                            CHANNEL_INFO.channel = info.getString("channel");
                            CHANNEL_INFO.channelName = info.getString("channel_name");
                            CHANNEL_INFO.channelProtocol = info.getString("channel_protocol");
                            CHANNEL_INFO.captchaLength = info.getInt("sms_code_length");
                        }catch (Exception e) {

                        }


                        RuntimeCache.saveConfig(info.toString());
                        updated = true;
                        LogUtil.d(Constant.TAG, "passport config updated successfully");
                    }

                    @Override
                    public void onError(Response<JSONObject> response) {
                        super.onError(response);
                        updated = false;
                        LogUtil.d(Constant.TAG, "passport config updated failed");

                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        if(CHANNEL_INFO.channelProtocol == null) {
                            CHANNEL_INFO.channelProtocol = "";
                        }
                        if(callback != null) {
                            callback.onConfigReceived(CHANNEL_INFO);
                        }
                    }
                });
    }

    public interface ConfigCallback {
        void onConfigReceived(ChannelInfoBean info);
    }
}
