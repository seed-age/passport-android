package cc.seedland.inf.passport.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

/**
 * 设备相关工具类
 * Created by xuchunlei on 2017/11/10.
 */

public class DeviceUtil {

    private static String MAC_ADDRESS = ""; // 设备Mac地址
    private static String DEVICE_ID = "";   // 设备唯一标识符
    private static final String DEFAULT_IP_ADDRESS;

    private static final String PREF_KEY_DEVICE_ID = "device_id";

    // 方便单元测试
    static {
        DEFAULT_IP_ADDRESS = "127.0.0.1";
    }

    private DeviceUtil() {

    }

    /**
     * 获取设备IP地址，不唯一，因此动态获取
     */
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements(); ) {
                NetworkInterface ni = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = ni.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception ex) {
            LogUtil.e(Constant.TAG, "DeviceUtil.getLocalIpAddress" + ex.toString());
        }
        return DEFAULT_IP_ADDRESS;
    }

    /**
     * 获取MAC地址，该方法兼容7.0及以下（8.0未测试）版本
     * @return 设备唯一的Mac地址
     */
    public static String getMacAddress() {
        if(MAC_ADDRESS.length() == 0) {
            try {
                List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
                for (NetworkInterface nif : all) {
                    if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                    byte[] macBytes = nif.getHardwareAddress();
                    if (macBytes == null) {
                        return null;
                    }

                    StringBuilder res1 = new StringBuilder();
                    for (byte b : macBytes) {
                        res1.append(String.format("%02X:", b));
                    }

                    if (res1.length() > 0) {
                        res1.deleteCharAt(res1.length() - 1);
                    }
                    MAC_ADDRESS = res1.toString();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                // TODO: 2017/11/13 此处应上报日志
            }
        }

        return MAC_ADDRESS;
    }

    /*----------------- 获取Device ID - begin -------------------*/

    /**
     * 获取设备唯一标识
     * @return
     */
    public static String getDeviceId() {
        if(DEVICE_ID.length() == 0) {
            // 优先从设置获取
            final Context context = Constant.APP_CONTEXT;
            DEVICE_ID = obtainFromSettings(context);
            // 其次从文件获取
//            if(TextUtils.isEmpty(DEVICE_ID)) {
//                DEVICE_ID = obtainFromFile(context);
//            }else {
//                saveToSettings(context, DEVICE_ID);
//            }
            // 再次生成
            if(DEVICE_ID.length() == 0) {
                final String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
                LogUtil.d(Constant.TAG, "DeviceUtil.getDeviceId:android_id---->" + androidId);
                try {
                    if(androidId != null && androidId.length() != 0) {
                        DEVICE_ID = UUID.nameUUIDFromBytes(androidId.getBytes("utf8")).toString();
                    }else {
                        final String macAddress = getMacAddress();
                        if(macAddress.length() != 0) {
                            DEVICE_ID = UUID.nameUUIDFromBytes(macAddress.getBytes("utf8")).toString();
                        }
                    }

                }catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Log.w(Constant.TAG, "failed to generate device id");
                }
                if(DEVICE_ID.length() == 0) { // 使用随机UUID
                    DEVICE_ID = UUID.randomUUID().toString();
                }

                // 保存
                saveToSettings(context, DEVICE_ID);
//                saveToFiles(context, DEVICE_ID);
            }
        }

        return DEVICE_ID;

    }

    /***
     *  本地的网络是否连通
     * @return
     */
    public static boolean isNetworkConnected() {

        // 判断是否具有可以用于通信渠道
        boolean mobileConnection = isMobileConnected(Constant.APP_CONTEXT);
        boolean wifiConnection = isWIFIConnected(Constant.APP_CONTEXT);
        return !(mobileConnection == false && wifiConnection == false);
    }

    /**
     * 判断手机接入点（APN）是否处于可以使用的状态
     *
     * @param context
     * @return
     */
    private static boolean isMobileConnected(Context context) {
        if(context == null) {
            return false;
        }
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(manager != null){
            NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断当前wifi是否是处于可以使用状态
     *
     * @param context
     * @return
     */
    private static boolean isWIFIConnected(Context context) {
        if(context == null) return false;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(manager != null){
            NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
        }

        return false;
    }

    private static void saveToSettings(Context context, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Constant.PREFS_SEEDLAND, Context.MODE_PRIVATE).edit();
        editor.putString(PREF_KEY_DEVICE_ID, value);
        editor.apply();
    }

    private static String obtainFromSettings(Context context) {
        final SharedPreferences prefs = context.getSharedPreferences(Constant.PREFS_SEEDLAND, Context.MODE_PRIVATE);
        return prefs.getString(PREF_KEY_DEVICE_ID, DEVICE_ID);
    }

//    private static void saveToFiles(Context context, String value) {
//        String path = context.getFilesDir() + "/seedland.txt";
//        File file = null;
//        try {
//            file = new File(path);
//            if (!file.exists()) {
//                if (!file.createNewFile()) {
//                    LogUtil.e(Constant.TAG, "failed to create file " + path);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
//            out.write(value.getBytes());
//            out.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static String obtainFromFile(Context context) {
//        String path = context.getFilesDir() + "/seedland.txt";
//        String result = null;
//        File file = new File(path);
//        byte[] buffer = new byte[32];
//        try {
//            InputStream in = new BufferedInputStream(new FileInputStream(file));
//            in.read(buffer);
//            result = new String(buffer);
//            in.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            return result;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }

    /*----------------- 获取Device ID - end -------------------*/

}
