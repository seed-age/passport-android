package cc.seedland.inf.passport.util;

import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings.Secure;
import android.test.mock.MockContentResolver;
import android.test.mock.MockContext;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Null;
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.Collections;

import cc.seedland.inf.passport.TestConstant;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.support.membermodification.MemberModifier.stub;

/**
 * Created by xuchunlei on 2017/12/22.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({DeviceUtil.class, NetworkInterface.class, Secure.class })
public class DeviceUtilTest {

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockStatic(NetworkInterface.class);
        Constant.APP_CONTEXT = mock(MockContext.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getLocalIpAddress() throws Exception {

        String defaultIp = "192.168.1.1";
        Whitebox.setInternalState(DeviceUtil.class, "DEFAULT_IP_ADDRESS", defaultIp);

        Inet4Address address1 = mock(Inet4Address.class);
        when(address1.getHostAddress()).thenReturn(TestConstant.DEVICE_IP_ADDRESS_V4);

        Inet4Address address2 = mock(Inet4Address.class);
        when(address2.getHostAddress()).thenReturn(TestConstant.DEVICE_IP_ADDRESS_LOOPBACK);
        when(address2.isLoopbackAddress()).thenReturn(true);

        Inet6Address address3 = mock(Inet6Address.class);
        when(address3.getHostAddress()).thenReturn(TestConstant.DEVICE_IP_ADDRESS_V6);

        InetAddress[] addresses1 = new InetAddress[] {
                address1,
                address3
        };

        InetAddress[] addresses2 = new InetAddress[] {
                address2,
                address3
        };

        InetAddress[] addresses3 = new InetAddress[] {
                address3
        };

        // mock interface1
        NetworkInterface interface1 = mock(NetworkInterface.class);
        when(interface1.getInetAddresses()).thenReturn(Collections.enumeration(Arrays.asList(addresses1)));
        Whitebox.setInternalState(interface1, "name", "interface1");
        // mock interface2
        NetworkInterface interface2 = mock(NetworkInterface.class);
        when(interface2.getInetAddresses()).thenReturn(Collections.enumeration(Arrays.asList(addresses2)));
        Whitebox.setInternalState(interface2, "name", "interface2");

        // mock interface3
        NetworkInterface interface3 = mock(NetworkInterface.class);
        when(interface3.getInetAddresses()).thenReturn(Collections.enumeration(Arrays.asList(addresses3)));
        Whitebox.setInternalState(interface3, "name", "interface3");

        String address;
        // 正确 - 返回ip
        NetworkInterface[] interfaces1 = new NetworkInterface[] {
                interface1,
                interface2
        };
        when(NetworkInterface.getNetworkInterfaces()).thenReturn(Collections.enumeration(Arrays.asList(interfaces1)));
        address = DeviceUtil.getLocalIpAddress();
        assertEquals(address, TestConstant.DEVICE_IP_ADDRESS_V4);

        // 正确 - 返回loopback地址
        NetworkInterface[] interfaces2 = new NetworkInterface[] {
                interface2
        };
        when(NetworkInterface.getNetworkInterfaces()).thenReturn(Collections.enumeration(Arrays.asList(interfaces2)));
        address = DeviceUtil.getLocalIpAddress();
        assertEquals(address, defaultIp);

        // 正确 - 返回默认地址
        NetworkInterface[] interfaces3 = new NetworkInterface[] {
                interface3
        };
        when(NetworkInterface.getNetworkInterfaces()).thenReturn(Collections.enumeration(Arrays.asList(interfaces3)));
        address = DeviceUtil.getLocalIpAddress();
        assertEquals(address, defaultIp);

    }

    @Test
    public void getMacAddress() throws Exception {

        String macAddress;

        // 已获取Mac地址
        Whitebox.setInternalState(DeviceUtil.class, "MAC_ADDRESS", TestConstant.DEVICE_MAC_ADDRESS);
        macAddress = DeviceUtil.getMacAddress();
        assertEquals(macAddress, TestConstant.DEVICE_MAC_ADDRESS);

        NetworkInterface interface1 = mock(NetworkInterface.class);
        when(interface1.getHardwareAddress()).thenReturn(new byte[] { 10, 20, 30, 40, 50, 60 });
        when(interface1.getName()).thenReturn("wlan0");

        NetworkInterface interface2 = mock(NetworkInterface.class);
        when(interface2.getHardwareAddress()).thenReturn(new byte[] { 11, 21, 31, 41, 51, 61 });
        when(interface2.getName()).thenReturn("hwsim0");

        NetworkInterface interface3 = mock(NetworkInterface.class);
        when(interface3.getHardwareAddress()).thenReturn(null);
        when(interface3.getName()).thenReturn("sit0");

        // 首次获取Mac地址
        NetworkInterface[] interfaces1 = new NetworkInterface[] {
                interface1,
                interface2,
                interface3
        };
        Whitebox.setInternalState(DeviceUtil.class, "MAC_ADDRESS", TestConstant.EMPTY_STRING);
        when(NetworkInterface.getNetworkInterfaces()).thenReturn(Collections.enumeration(Arrays.asList(interfaces1)));
        macAddress = DeviceUtil.getMacAddress();
        assertEquals(macAddress, "0A:14:1E:28:32:3C");

        // 未获取到Mac地址
        NetworkInterface[] interfaces2 = new NetworkInterface[] {
                interface2,
                interface3
        };
        Whitebox.setInternalState(DeviceUtil.class, "MAC_ADDRESS", TestConstant.EMPTY_STRING);
        when(NetworkInterface.getNetworkInterfaces()).thenReturn(Collections.enumeration(Arrays.asList(interfaces2)));
        macAddress = DeviceUtil.getMacAddress();
        assertEquals(macAddress, TestConstant.EMPTY_STRING);

    }

    @Test
    public void getDeviceId() throws Exception {

        MockContentResolver resolver = mock(MockContentResolver.class);
        when(Constant.APP_CONTEXT.getContentResolver()).thenReturn(resolver);
        SharedPreferences prefs = mock(SharedPreferences.class);
        when(Constant.APP_CONTEXT.getSharedPreferences(anyString(), anyInt())).thenReturn(prefs);
        when(prefs.edit()).thenReturn(mock(SharedPreferences.Editor.class));

        // 从缓存的配置项获取
        Whitebox.setInternalState(DeviceUtil.class, "DEVICE_ID", TestConstant.EMPTY_STRING);
        stub(MemberMatcher.method(DeviceUtil.class, "obtainFromSettings")).toReturn("1234-567890-abcd-ef123456");
        assertEquals(DeviceUtil.getDeviceId(), "1234-567890-abcd-ef123456");

        // 关闭缓存
        stub(MemberMatcher.method(DeviceUtil.class, "obtainFromSettings")).toReturn(TestConstant.EMPTY_STRING);

        // 由android_id生成
        Whitebox.setInternalState(DeviceUtil.class, "DEVICE_ID", TestConstant.EMPTY_STRING);
        mockStatic(Secure.class);
        when(Secure.getString(any(MockContentResolver.class), anyString())).thenReturn(TestConstant.DEVICE_ANDROID_ID);
        String deviceId = DeviceUtil.getDeviceId();
        assertEquals(deviceId, TestConstant.DEVICE_ID_BY_ANDROID_ID);

        // 由Mac地址生成
        Whitebox.setInternalState(DeviceUtil.class, "DEVICE_ID", TestConstant.EMPTY_STRING);
        stub(MemberMatcher.method(DeviceUtil.class, "getMacAddress")).toReturn(TestConstant.DEVICE_MAC_ADDRESS);
        when(Secure.getString(any(ContentResolver.class), anyString())).thenReturn(TestConstant.EMPTY_STRING);
        assertEquals(DeviceUtil.getDeviceId(), TestConstant.DEVICE_ID_BY_MAC_ADDRESS);

        // 随机生成
        Whitebox.setInternalState(DeviceUtil.class, "DEVICE_ID", TestConstant.EMPTY_STRING);
        stub(MemberMatcher.method(DeviceUtil.class, "getMacAddress")).toReturn(TestConstant.EMPTY_STRING);
        when(Secure.getString(any(ContentResolver.class), anyString())).thenReturn(TestConstant.EMPTY_STRING);
        deviceId = DeviceUtil.getDeviceId();
        assertNotEquals(deviceId, TestConstant.DEVICE_ID_BY_ANDROID_ID);
        assertNotEquals(deviceId, TestConstant.DEVICE_ID_BY_MAC_ADDRESS);

    }

    @Test
    public void isNetworkConnected() throws Exception {
        stub(MemberMatcher.method(DeviceUtil.class, "isWIFIConnected")).toReturn(true);
        stub(MemberMatcher.method(DeviceUtil.class, "isMobileConnected")).toReturn(true);
        assertTrue(DeviceUtil.isNetworkConnected());

        stub(MemberMatcher.method(DeviceUtil.class, "isWIFIConnected")).toReturn(false);
        stub(MemberMatcher.method(DeviceUtil.class, "isMobileConnected")).toReturn(true);
        assertTrue(DeviceUtil.isNetworkConnected());

        stub(MemberMatcher.method(DeviceUtil.class, "isWIFIConnected")).toReturn(true);
        stub(MemberMatcher.method(DeviceUtil.class, "isMobileConnected")).toReturn(false);
        assertTrue(DeviceUtil.isNetworkConnected());

        stub(MemberMatcher.method(DeviceUtil.class, "isWIFIConnected")).toReturn(false);
        stub(MemberMatcher.method(DeviceUtil.class, "isMobileConnected")).toReturn(false);
        assertFalse(DeviceUtil.isNetworkConnected());
    }

    @Test
    public void isWifiConnected() throws Exception {
        ConnectivityManager manager = mock(ConnectivityManager.class);
        NetworkInfo ni = mock(NetworkInfo.class);

        // false - 上下文为空
        boolean wifi = Whitebox.invokeMethod(DeviceUtil.class, "isWIFIConnected", null);
        assertFalse(wifi);
        verify(Constant.APP_CONTEXT, times(0)).getSystemService(anyString());

        // false - 网络接口为空
        when(manager.getNetworkInfo(anyInt())).thenReturn(null);
        wifi = Whitebox.invokeMethod(DeviceUtil.class, "isWIFIConnected", Constant.APP_CONTEXT);
        assertFalse(wifi);
        verify(Constant.APP_CONTEXT, times(1)).getSystemService(anyString());
        verify(manager, times(0)).getNetworkInfo(anyInt());

        // true - wifi连接
        when(manager.getNetworkInfo(anyInt())).thenReturn(ni);
        when(ni.isConnected()).thenReturn(true);
        when(Constant.APP_CONTEXT.getSystemService(anyString())).thenReturn(manager);
        wifi = Whitebox.invokeMethod(DeviceUtil.class, "isWIFIConnected", Constant.APP_CONTEXT);
        assertTrue(wifi);
        verify(Constant.APP_CONTEXT, times(2)).getSystemService(anyString());
        verify(manager, times(1)).getNetworkInfo(anyInt());

//        stub(MemberMatcher.method(DeviceUtil.class, "isMobileConnected")).toReturn(false);
//        assertTrue(DeviceUtil.isNetworkConnected());
    }

    @Test
    public void isMobileConnected() throws Exception {
        ConnectivityManager manager = mock(ConnectivityManager.class);
        NetworkInfo ni = mock(NetworkInfo.class);
        final String methodName = "isMobileConnected";

        // false - 上下文为空
        boolean wifi = Whitebox.invokeMethod(DeviceUtil.class, methodName, null);
        assertFalse(wifi);
        verify(Constant.APP_CONTEXT, times(0)).getSystemService(anyString());

        // false - 网络接口为空
        when(manager.getNetworkInfo(anyInt())).thenReturn(null);
        wifi = Whitebox.invokeMethod(DeviceUtil.class, methodName, Constant.APP_CONTEXT);
        assertFalse(wifi);
        verify(Constant.APP_CONTEXT, times(1)).getSystemService(anyString());
        verify(manager, times(0)).getNetworkInfo(anyInt());

        // true - wifi连接
        when(manager.getNetworkInfo(anyInt())).thenReturn(ni);
        when(ni.isConnected()).thenReturn(true);
        when(Constant.APP_CONTEXT.getSystemService(anyString())).thenReturn(manager);
        wifi = Whitebox.invokeMethod(DeviceUtil.class, methodName, Constant.APP_CONTEXT);
        assertTrue(wifi);
        verify(Constant.APP_CONTEXT, times(2)).getSystemService(anyString());
        verify(manager, times(1)).getNetworkInfo(anyInt());

//        stub(MemberMatcher.method(DeviceUtil.class, "isWIFIConnected")).toReturn(false);
//        assertTrue(DeviceUtil.isNetworkConnected());
    }

}