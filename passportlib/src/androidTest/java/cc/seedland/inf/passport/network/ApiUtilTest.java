package cc.seedland.inf.passport.network;

import android.app.Instrumentation;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.test.mock.MockContext;
import android.util.Log;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.Map;

import cc.seedland.inf.passport.ReflectUtil;
import cc.seedland.inf.passport.TestConstant;
import cc.seedland.inf.passport.util.Constant;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Created by xuchunlei on 2017/12/20.
 */
public class ApiUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ApiUtil instance;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Constant.APP_CONTEXT = getInstrumentation().getTargetContext();
        ApiUtil.init(TestConstant.CHANNEL, TestConstant.KEY, TestConstant.HOST_RIGHT_1);

        instance = ReflectUtil.createFromPrivateConstructor(ApiUtil.class.getName());
        ReflectUtil.setValue(instance, "PARENT_PATH", TestConstant.API_ROOT_PATH);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void init() throws Exception {

        // 正确
        ApiUtil.init(TestConstant.CHANNEL, TestConstant.KEY, TestConstant.HOST_RIGHT_1);
        Assert.assertEquals(TestConstant.CHANNEL, ReflectUtil.getValue(instance, "CHANNEL"));
        Assert.assertEquals(TestConstant.KEY, ReflectUtil.getValue(instance, "AUTH_KEY"));
        Assert.assertEquals(TestConstant.HOST_RIGHT_1, ReflectUtil.getValue(instance, "HOST"));

        // 正确
        ApiUtil.init(TestConstant.CHANNEL, TestConstant.KEY, TestConstant.HOST_RIGHT_2);
        Assert.assertEquals(TestConstant.CHANNEL, ReflectUtil.getValue(instance, "CHANNEL"));
        Assert.assertEquals(TestConstant.KEY, ReflectUtil.getValue(instance, "AUTH_KEY"));
        Assert.assertEquals(TestConstant.HOST_RIGHT_2, ReflectUtil.getValue(instance, "HOST"));

        // 错误
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("invalid host name ");
        ApiUtil.init(TestConstant.CHANNEL, TestConstant.KEY, TestConstant.HOST_WRONG_1);
        ApiUtil.init(TestConstant.CHANNEL, TestConstant.KEY, TestConstant.HOST_WRONG_2);

    }

    @Test
    public void generateUrlForPost() throws Exception {
        Map<String, String> commonParams = ReflectUtil.callPrivateMethod(ApiUtil.class.getName(), "getCommonParams");
        String sign = ReflectUtil.callPrivateMethod(ApiUtil.class.getName(), "signApi", new Class<?>[]{Map.class}, commonParams);
        String signUrl = ApiUtil.generateUrlForPost(TestConstant.API_PATH);

        Uri uri = Uri.parse(signUrl);
        assertEquals(TestConstant.HOST_RIGHT_1, uri.getScheme() + "://" + uri.getHost());
        assertEquals("/" + TestConstant.API_ROOT_PATH + TestConstant.API_PATH, uri.getPath());
        assertEquals(commonParams.get("channel"), uri.getQueryParameter("channel"));
        assertEquals(commonParams.get("timestamp"), uri.getQueryParameter("timestamp"));
        assertEquals(commonParams.get("client_ip"), uri.getQueryParameter("client_ip"));
        assertEquals(commonParams.get("device_type"), uri.getQueryParameter("device_type"));
        assertEquals(commonParams.get("device_mac"), uri.getQueryParameter("device_mac"));
        assertEquals(commonParams.get("device_imei"), uri.getQueryParameter("device_imei"));
        assertEquals(sign.substring(sign.lastIndexOf("auth=") + 5), uri.getQueryParameter("auth"));
        assertEquals(uri.getQueryParameterNames().size(), commonParams.size() + 1);
    }

    @Test
    public void generalUrlForGet() throws Exception {
    }

    @Test
    public void generateParamsBodyForPost() throws Exception {
    }

    @Test
    public void refreshToken() throws Exception {


    }

}