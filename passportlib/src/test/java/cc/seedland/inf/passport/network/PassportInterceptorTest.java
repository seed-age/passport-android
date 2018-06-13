package cc.seedland.inf.passport.network;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.TestConstant;
import cc.seedland.inf.passport.common.LoginBean;
import cc.seedland.inf.passport.util.Constant;
import okhttp3.Interceptor.Chain;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Response.Builder;
import okhttp3.ResponseBody;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by xuchunlei on 2018/1/3.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ Request.class, DeviceUtil.class, Constant.class, PassportInterceptor.class, Response.class, ResponseBody.class })
public class PassportInterceptorTest {

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void intercept() throws Exception {
        PassportInterceptor interceptor = new PassportInterceptor();

        // mock
        mockStatic(DeviceUtil.class);
        mockStatic(Constant.class);
        when(Constant.getString(R.string.error_network)).thenReturn("network error");

        Chain chain = mock(Chain.class);
        Request request = mock(Request.class);
        when(chain.request()).thenReturn(request);
        Response response = mock(Response.class);
        when(chain.proceed(request)).thenReturn(response);

        ResponseBody body = mock(ResponseBody.class);
        when(response.body()).thenReturn(body);

        // 失败 - 网络未连接
        when(DeviceUtil.isNetworkConnected()).thenReturn(false);
//        IOException exception = new IOException("network error");
//        whenNew(IOException.class).withAnyArguments().thenReturn(exception);
        try {
            interceptor.intercept(chain);
            fail();
        }catch (Exception e) {
            assertEquals(e.getMessage(), "network error");
        }

        // 失败 - 返回错误

        when(body.string()).thenReturn(TestConstant.RESPONSE_BODY_FALIED);
        when(DeviceUtil.isNetworkConnected()).thenReturn(true);
        try {
            interceptor.intercept(chain);
        }catch (Exception e) {
            assertEquals(e.getMessage(), "帐号或密码输入有误，请重新输入");
        }

        // 成功

        Builder builder = spy(Builder.class);
        Whitebox.setInternalState(builder, "request", request);
        Whitebox.setInternalState(builder, "protocol", Protocol.HTTP_1_1);
        Whitebox.setInternalState(builder, "message", "success");
        when(response.newBuilder()).thenReturn(builder);
        when(response.code()).thenReturn(200);
        when(body.string()).thenReturn(TestConstant.RESPONSE_BODY_SUCCESS);
        Response retResponse = interceptor.intercept(chain);
        assertEquals(retResponse.code(), 200);
        LoginBean bean = GsonHolder.getInstance().fromJson(retResponse.body().string(), LoginBean.class);
        assertEquals(bean.token, "WVr5CyKwtEH1wAjXPG7AlNDZRb9FKvnAVhB8ppliGVnGpaTHryWoBgoiv56kMy1Nd9cC3N7ufASX7C3BoEXh/Wy8XYI7InM4e3RN6PUccHxejHy5LJiuk3cOrkDMyl22vO7tEu9UmKUJ5bTWkeOg2Nz+gpoeNA9u+rfPIRjh3gQ=");
        assertEquals(bean.mobile, "13800138000");
        assertEquals(bean.nickname, "User_13800138000");
        assertEquals(bean.raw, TestConstant.RESPONSE_BODY_SUCCESS);
        assertEquals(bean.uid, 100);
    }

}