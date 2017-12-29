package cc.seedland.inf.passport.login;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.PostRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Map;

import cc.seedland.inf.passport.TestConstant;
import cc.seedland.inf.passport.base.BaseBean;
import cc.seedland.inf.passport.common.LoginBean;
import cc.seedland.inf.passport.network.ApiUtil;
import cc.seedland.inf.passport.network.BizCallback;
import cc.seedland.inf.passport.util.DeviceUtil;
import okhttp3.RequestBody;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by xuchunlei on 2017/12/28.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ OkGo.class, ApiUtil.class, DeviceUtil.class })
public class LoginModelTest {

    @Mock
    private PostRequest<LoginBean> request;
    @Mock
    private RequestBody requestBody;
    @Mock
    private BizCallback<LoginBean> callback;

    private LoginModel model;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockStatic(OkGo.class);
        mockStatic(DeviceUtil.class);

        mockStatic(ApiUtil.class);
        when(ApiUtil.generateUrlForPost(anyString())).thenReturn(TestConstant.API);
        when(ApiUtil.generateParamsBodyForPost(any(Map.class))).thenReturn(requestBody);
        when(OkGo.<LoginBean>post(anyString())).thenReturn(request);
        when(request.upRequestBody(requestBody)).thenReturn(request);

        model = new LoginModel();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void loginByPassword() throws Exception {
        model.loginByPassword(TestConstant.PHONE_RIGHT_1, TestConstant.PASSWORD_RIGHT_1, callback);
        verifyStatic(OkGo.class);
        OkGo.<LoginBean>post(anyString());
        verify(request).execute(eq(callback));
        verify(request).upRequestBody(eq(requestBody));
    }

    @Test
    public void loginByCaptcha() throws Exception {
        model.loginByCaptcha(TestConstant.PHONE_RIGHT_1, "1234", callback);
        verifyStatic(OkGo.class);
        OkGo.<LoginBean>post(anyString());
        verify(request).execute(eq(callback));
        verify(request).upRequestBody(eq(requestBody));
    }

    @Test
    public void obtainCaptcha() throws Exception {
        BizCallback<BaseBean> captchaCallback = mock(BizCallback.class);
        PostRequest<BaseBean> captchaRequest = mock(PostRequest.class);
        when(OkGo.<BaseBean>post(anyString())).thenReturn(captchaRequest);
        when(captchaRequest.upRequestBody(requestBody)).thenReturn(captchaRequest);

        model.obtainCaptcha(TestConstant.PHONE_RIGHT_1, captchaCallback);
        verifyStatic(OkGo.class);
        OkGo.<BaseBean>post(anyString());
        verify(captchaRequest).execute(eq(captchaCallback));
        verify(captchaRequest).upRequestBody(eq(requestBody));
    }

}