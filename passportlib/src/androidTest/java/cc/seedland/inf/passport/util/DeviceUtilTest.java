package cc.seedland.inf.passport.util;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.text.TextUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;
import java.util.regex.Pattern;

import cc.seedland.inf.passport.ReflectUtil;
import cc.seedland.inf.passport.TestConstant;

import static android.support.test.InstrumentationRegistry.getContext;
import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.*;

/**
 * Created by xuchunlei on 2017/12/26.
 */
public class DeviceUtilTest {

    private static final Pattern REGEX_IPV4_ADDRESS = Pattern.compile("(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)");

    @Before
    public void setUp() throws Exception {
        Constant.APP_CONTEXT = getTargetContext();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getLocalIpAddress() throws Exception {
        String ip = DeviceUtil.getLocalIpAddress();
        assertTrue(REGEX_IPV4_ADDRESS.matcher(ip).matches());
        assertNotEquals(ip, TestConstant.DEVICE_IP_ADDRESS_LOOPBACK);
    }

    @Test
    public void getMacAddress() throws Exception {
        String macAddress = DeviceUtil.getMacAddress();
        ReflectUtil.setValue(DeviceUtil.class, "MAC_ADDRESS", "");
        assertEquals(macAddress, DeviceUtil.getMacAddress());
    }

    @Test
    public void getDeviceId() throws Exception {
        String androidId = Settings.Secure.getString(Constant.APP_CONTEXT.getContentResolver(), Settings.Secure.ANDROID_ID);
        String macAddress = DeviceUtil.getMacAddress();
        if(!TextUtils.isEmpty(androidId)) {
            assertEquals(UUID.nameUUIDFromBytes(androidId.getBytes("utf8")).toString(), DeviceUtil.getDeviceId());
        }else if(!TextUtils.isEmpty(macAddress)) {
            assertEquals(UUID.nameUUIDFromBytes(macAddress.getBytes("utf8")).toString(), DeviceUtil.getDeviceId());
        }else {
            assertEquals(DeviceUtil.getDeviceId(), ReflectUtil.callPrivateMethod(DeviceUtil.class.getName(),
                    "obtainFromSettings", new Class[] { Context.class }, new Object[] { Constant.APP_CONTEXT }));
        }
    }

    @Test
    public void isNetworkConnected() throws Exception {
        boolean mobile = ReflectUtil.callPrivateMethod(DeviceUtil.class.getName(), "isMobileConnected", new Class[] { Context.class }, new Object[] { Constant.APP_CONTEXT });
        boolean wifi = ReflectUtil.callPrivateMethod(DeviceUtil.class.getName(), "isWIFIConnected", new Class[] { Context.class }, new Object[] { Constant.APP_CONTEXT });

        if(mobile == false && wifi == false) {
            assertFalse(DeviceUtil.isNetworkConnected());
        }else {
            assertTrue(DeviceUtil.isNetworkConnected());
        }
    }

}