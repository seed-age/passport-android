package cc.seedland.inf.passport.password;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.PostRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Map;

import cc.seedland.inf.passport.TestConstant;
import cc.seedland.inf.passport.common.LoginBean;
import cc.seedland.inf.passport.common.SimpleBean;
import cc.seedland.inf.passport.network.ApiUtil;
import cc.seedland.inf.passport.network.BizCallback;
import cc.seedland.inf.passport.network.RuntimeCache;
import okhttp3.RequestBody;

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
 * Created by xuchunlei on 2017/12/29.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ApiUtil.class, OkGo.class, RuntimeCache.class })
public class PasswordModelTest {

    private PasswordModel model;
    @Mock
    private RequestBody requestBody;

    @Mock
    private PostRequest<BaseBean> baseRequest;
    @Mock
    private PostRequest<SimpleBean> simpleRequest;
    @Mock
    private PostRequest<LoginBean> loginRequest;

    @Before
    public void setUp() throws Exception {
        model = new PasswordModel();

        mockStatic(ApiUtil.class);
        when(ApiUtil.generateParamsBodyForPost(any(Map.class))).thenReturn(requestBody);
        when(ApiUtil.generateUrlForPost(anyString())).thenReturn(TestConstant.API);

        mockStatic(OkGo.class);

        when(baseRequest.upRequestBody(requestBody)).thenReturn(baseRequest);
        when(simpleRequest.upRequestBody(requestBody)).thenReturn(simpleRequest);
        when(loginRequest.upRequestBody(requestBody)).thenReturn(loginRequest);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void obtainCaptcha() throws Exception {

        when(OkGo.<BaseBean>post(anyString())).thenReturn(baseRequest);

        BizCallback<BaseBean> callback = mock(BizCallback.class);
        model.obtainCaptcha(TestConstant.PHONE_RIGHT_1, callback);

        verifyStatic(OkGo.class);
        OkGo.<BaseBean>post(anyString());
        verify(baseRequest).upRequestBody(eq(requestBody));
        verify(baseRequest).execute(eq(callback));
    }

    @Test
    public void reset() throws Exception {

        when(OkGo.<SimpleBean>post(anyString())).thenReturn(simpleRequest);

        BizCallback<SimpleBean> callback = mock(BizCallback.class);
        model.reset(TestConstant.PHONE_RIGHT_1, TestConstant.PASSWORD_RIGHT_1, TestConstant.CAPTCHA_RIGHT_1, callback);

        verifyStatic(OkGo.class);
        OkGo.<SimpleBean>post(anyString());
        verify(simpleRequest).upRequestBody(eq(requestBody));
        verify(simpleRequest).execute(eq(callback));
    }

    @Test
    public void modify() throws Exception {

        BizCallback<LoginBean> callback = mock(BizCallback.class);
        mockStatic(RuntimeCache.class);
        when(RuntimeCache.getToken()).thenReturn(TestConstant.RESPONSE_TOKEN);
        when(OkGo.<LoginBean>post(anyString())).thenReturn(loginRequest);

        model.modify(TestConstant.PASSWORD_RIGHT_1, TestConstant.PASSWORD_RIGHT_3, callback);
        verifyStatic(OkGo.class);
        OkGo.<LoginBean>post(anyString());
        verify(loginRequest).upRequestBody(eq(requestBody));
        verify(loginRequest).execute(eq(callback));
    }

}