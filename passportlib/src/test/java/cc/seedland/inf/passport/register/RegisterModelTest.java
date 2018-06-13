package cc.seedland.inf.passport.register;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.PostRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import cc.seedland.inf.passport.TestConstant;
import cc.seedland.inf.passport.network.BizCallback;
import okhttp3.RequestBody;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by xuchunlei on 2018/1/2.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({ OkGo.class, DeviceUtil.class })
public class RegisterModelTest {

    private RegisterModel model;

    @Before
    public void setUp() throws Exception {

        mockStatic(OkGo.class);
        mockStatic(DeviceUtil.class);

        model = new RegisterModel();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getCaptcha() throws Exception {

        BizCallback<BaseBean> callback = mock(BizCallback.class);
        PostRequest<BaseBean> request = mock(PostRequest.class);
        when(OkGo.<BaseBean>post(anyString())).thenReturn(request);
        when(request.upRequestBody(any(RequestBody.class))).thenReturn(request);

        model.getCaptcha(TestConstant.PHONE_RIGHT_1, callback);
        verifyStatic(OkGo.class);
        OkGo.post(anyString());
        verify(request).execute(eq(callback));

    }

    @Test
    public void performPhone() throws Exception {
        BizCallback<RegisterBean> callback = mock(BizCallback.class);
        PostRequest<RegisterBean> request = mock(PostRequest.class);
        when(OkGo.<RegisterBean>post(anyString())).thenReturn(request);
        when(request.upRequestBody(any(RequestBody.class))).thenReturn(request);

        model.performPhone(TestConstant.PHONE_RIGHT_1, TestConstant.PASSWORD_RIGHT_1, TestConstant.CAPTCHA_RIGHT_1, callback);
        verifyStatic(OkGo.class);
        OkGo.post(anyString());
        verify(request).execute(eq(callback));
    }

}