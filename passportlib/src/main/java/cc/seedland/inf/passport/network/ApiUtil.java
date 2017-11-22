package cc.seedland.inf.passport.network;

import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import cc.seedland.inf.passport.base.BaseBean;
import cc.seedland.inf.passport.common.TokenBean;
import cc.seedland.inf.passport.util.Constant;
import cc.seedland.inf.passport.util.DeviceUtil;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 构造API请求时使用
 * Created by xuchunlei on 2017/11/13.
 */

public class ApiUtil {

    // API使用的Host
    private static String HOST;
    private static final String PARENT_PATH = "/api/rest";
    // 用于Api签名的key, 可视为常量
    private static String AUTH_KEY;
    // 用于Api请求参数，可视为常量
    private static String CHANNEL;
    // MD5算法生成字符串时补位使用
    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    /**
     * post请求支持utf-8
     */
    private static final MediaType FORM_CONTENT_TYPE
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

    private ApiUtil(){

    }

    /**
     * 初始化方法
     * @param channel 开发者渠道号
     * @param key 开发者Key，用于做API签名
     * @param host Api请求的Host名
     */
    public static void init(String channel, String key, String host) {

        CHANNEL = channel;
        AUTH_KEY = key;
        HOST = host;

        refreshToken();
    }


    /**
     * 生成Post请求地址
     * @param path
     * @return
     */
    public static String generateUrlForPost(String path) {
        return generateUrl(path) + signApi(getCommonParams());
    }

    /**
     * 生成Get请求地址
     * @param path
     * @param params
     * @return
     */
    public static String generalUrlForGet(String path, Map<String, String> params) {
        Map<String, String> rawParams = getCommonParams();
        rawParams.putAll(params);
        return generateUrl(path) + signApi(rawParams);
    }

    /**
     * 生成Post请求体
     * @param params
     * @return
     */
    public static RequestBody generateParamsBodyForPost(Map<String, String> params) {

        return RequestBody.create(FORM_CONTENT_TYPE, generateQueryString(params, true));
    }

    // 刷新Token
    public static void refreshToken() {
        String cachedToken = RuntimeCache.getToken();
        if(!TextUtils.isEmpty(cachedToken)) { // 缓存过Token，则进行刷新
            Map<String, String> params = new HashMap<>();
            params.put("sso_tk", RuntimeCache.getToken());
            OkGo.<TokenBean>post(generateUrlForPost(Constant.API_URL_TOKEN))
                    .upRequestBody(generateParamsBodyForPost(params))
                    .execute(new JsonCallback<TokenBean>(TokenBean.class) {
                        @Override
                        public void onSuccess(Response<TokenBean> response) {
                            RuntimeCache.saveToken(response.body().token);

                            if(Constant.DEBUG) {
                                Toast.makeText(Constant.APP_CONTEXT, RuntimeCache.getToken(), Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onError(Response<TokenBean> response) {
                            super.onError(response);
                            if(Constant.DEBUG) {
                                Toast.makeText(Constant.APP_CONTEXT, response.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }

    }

    /**
     * 检验签名　
     * @param timestamp
     * @param sign
     * @return
     */
    public static boolean checkSign(long timestamp, String sign) {
        String checkSign = ApiUtil.MD5(CHANNEL + "." + timestamp);
        return checkSign.equals(sign);
    }

    public static String testSign(TreeMap<String, String> params, String key) {

        // 生成签名
        String signQuery = generateQueryString(params, false);
        signQuery = signQuery + "&" + key;
        Log.e("xuchunlei", signQuery);

        return encode(MD5(signQuery));

    }

    public static <T extends BaseBean> String testPost(String url, Map<String, String> commonParams, Map<String, String> params, String sign) {
        try {

            // 请求参数
            String baseQuery = generateQueryString(commonParams, true);
            String quary = baseQuery + "&" + encode("auth") + "=" + sign;


            return OkGo.<T>post(url + "?" + quary)
                    .upRequestBody(ApiUtil.generateParamsBodyForPost(params))
                    .execute()
                    .body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "nothing";
    }

    /**
     * 生成Api请求的Url
     * @param path
     * @return
     */
    private static String generateUrl(String path) {
        return HOST + PARENT_PATH + path + "?";
    }

    /**
     * 获取公共参数
     * @return
     */
    private static Map<String, String> getCommonParams() {
        Map<String, String> params = new TreeMap<>();
        params.put("channel", CHANNEL);
        params.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        params.put("client_ip", DeviceUtil.getLocalIpAddress());
        params.put("device_type", Build.MANUFACTURER + "-" + Build.MODEL);
        params.put("device_mac", DeviceUtil.getMacAddress());
        params.put("device_imei", DeviceUtil.getDeviceId());

        return params;
    }

    /**
     * 签名API的get参数
     * @param params
     * @return
     */
    private static String signApi(Map<String, String> params) {

        // 请求参数
        String baseQuery = generateQueryString(params, true);

        // 生成签名
        String signQuery = generateQueryString(params, false);
        signQuery = signQuery + "&" + AUTH_KEY;

        return baseQuery + "&" + encode("auth") + "=" + encode(MD5(signQuery));
    }

    /**
     * 生成请求参数地址
     * @param params
     * @return
     */
    private static String generateQueryString(Map<String, String> params, boolean encodeFlag) {
        StringBuilder paramSb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if(TextUtils.isEmpty(value)) { // 去除值为空的参数
                continue;
            }
            paramSb.append(encodeFlag ? encode(key) : key);
            paramSb.append("=");
            paramSb.append(encodeFlag ? encode(value) : value);
            paramSb.append("&");
        }
        paramSb.deleteCharAt(paramSb.length() - 1);
        return paramSb.toString();

    }

    private static String MD5(String s) {

        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = HEX_DIGITS[byte0 >>> 4 & 0xf];
                str[k++] = HEX_DIGITS[byte0 & 0xf];
            }
            return new String(str).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String encode(String value) {
        return Uri.encode(value, "UTF-8");
    }

}
