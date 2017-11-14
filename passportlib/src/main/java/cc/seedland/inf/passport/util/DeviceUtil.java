package cc.seedland.inf.passport.util;

import android.content.Context;
import android.content.SharedPreferences;
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

    private static String MAC_ADDRESS; // 设备Mac地址
    private static String DEVICE_ID;   // 设备唯一标识符

    private static final String PREF_KEY_DEVICE_ID = "device_id";

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
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception ex) {
            Log.e(Constant.TAG, "DeviceUtil.getLocalIpAddress" + ex.toString());
        }
        return null;
    }

    /**
     * 获取MAC地址，该方法兼容6.0及以上版本
     * @return 设备唯一的Mac地址
     */
    public static String getMacAddress() {
        if(TextUtils.isEmpty(MAC_ADDRESS)) {
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
//                        res1.append(Integer.toHexString(b & 0xff ) + ":");
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
        if(TextUtils.isEmpty(DEVICE_ID)) {
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
            if(TextUtils.isEmpty(DEVICE_ID)) {
                final String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
                LogUtil.d(Constant.TAG, "DeviceUtil.getDeviceId:android_id---->" + androidId);
                try {
                    if(!TextUtils.isEmpty(androidId)) {
                        DEVICE_ID = UUID.nameUUIDFromBytes(androidId.getBytes("utf8")).toString();
                    }else {
                        final String macAddress = getMacAddress();
                        if(!TextUtils.isEmpty(macAddress)) {
                            DEVICE_ID = UUID.nameUUIDFromBytes(macAddress.getBytes("utf8")).toString();
                        }
                    }

                }catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Log.w(Constant.TAG, "failed to generate device id");
                }
                if(TextUtils.isEmpty(DEVICE_ID)) { // 使用随机UUID
                    DEVICE_ID = UUID.randomUUID().toString();
                }

                // 保存
                saveToSettings(context, DEVICE_ID);
//                saveToFiles(context, DEVICE_ID);
            }
        }

        return DEVICE_ID;

    }

    private static void saveToSettings(Context context, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Constant.PREFS_SEEDLAND, Context.MODE_PRIVATE).edit();
        editor.putString(PREF_KEY_DEVICE_ID, value);
        editor.apply();
    }

    private static String obtainFromSettings(Context context) {
        final SharedPreferences prefs = context.getSharedPreferences(Constant.PREFS_SEEDLAND, Context.MODE_PRIVATE);
        return prefs.getString(PREF_KEY_DEVICE_ID, null);
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
