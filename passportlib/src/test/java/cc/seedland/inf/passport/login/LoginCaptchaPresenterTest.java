package cc.seedland.inf.passport.login;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.test.mock.MockContext;

import com.lzy.okgo.model.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import cc.seedland.inf.passport.TestConstant;
import cc.seedland.inf.passport.base.BaseBean;
import cc.seedland.inf.passport.common.ICaptchaView;
import cc.seedland.inf.passport.common.LoginBean;
import cc.seedland.inf.passport.network.BizCallback;
import cc.seedland.inf.passport.network.RuntimeCache;
import cc.seedland.inf.passport.util.Constant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Created by xuchunlei on 2017/12/27.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest( { LoginCaptchaPresenter.class, Constant.class, Response.class, RuntimeCache.class})
public class LoginCaptchaPresenterTest {

    private LoginCaptchaPresenter presenter;

    @Mock
    private ICaptchaView view;
    @Captor
    private ArgumentCaptor<BizCallback<BaseBean>> captchaCaptor;
    @Captor
    private ArgumentCaptor<BizCallback<LoginBean>> loginCaptor;

    @Mock
    private LoginModel model;

    @Mock
    private MockContext context;
    @Mock
    private SharedPreferences prefs;
    @Mock
    private SharedPreferences.Editor editor;
    @Mock
    private Response<LoginBean> loginResponse;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Constant.APP_CONTEXT = context;
        when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(prefs);
        when(prefs.edit()).thenReturn(editor);
        whenNew(LoginModel.class).withNoArguments().thenReturn(model);

        presenter = new LoginCaptchaPresenter();
        presenter.attach(view);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void performCaptcha() throws Exception {

        mockStatic(Constant.class);
        when(Constant.getString(anyInt())).thenReturn("phone error");

        // 失败
        presenter.performCaptcha(TestConstant.PHONE_WRONG_1);
        verify(model, times(0)).obtainCaptcha(eq(TestConstant.PHONE_WRONG_1), captchaCaptor.capture());
        verify(view, times(0)).startWaitingCaptcha();
        verify(view).showToast(eq("phone error"));

        // 成功
        presenter.performCaptcha(TestConstant.PHONE_RIGHT_1);
        verify(model).obtainCaptcha(eq(TestConstant.PHONE_RIGHT_1), captchaCaptor.capture());
        captchaCaptor.getValue().onSuccess(mock(Response.class));
        verify(view).startWaitingCaptcha();

    }

    @Test
    public void perform() throws Exception {

        mockStatic(Constant.class);
        when(Constant.getString(Constant.ERROR_CODE_PHONE_FORMAT)).thenReturn("phone error");
        when(Constant.getString(Constant.ERROR_CODE_CAPTCHA_EMPTY)).thenReturn("captcha error");
        String captcha = "1234";

        // 失败 - 手机号码错误
        presenter.perform(TestConstant.PHONE_WRONG_1, anyString());
        verify(model, times(0)).loginByCaptcha(eq(TestConstant.PHONE_WRONG_1), eq(captcha), loginCaptor.capture());
        verify(view).showToast("phone error");

        // 失败 - 验证码错误
        presenter.perform(TestConstant.PHONE_RIGHT_1, TestConstant.EMPTY_STRING);
        verify(model, times(0)).loginByCaptcha(eq(TestConstant.PHONE_RIGHT_1), eq(TestConstant.EMPTY_STRING), loginCaptor.capture());
        verify(view).showToast("captcha error");

        // 成功

        // mock response
        LoginBean bean = mock(LoginBean.class);
        when(loginResponse.body()).thenReturn(bean);
        bean.token = TestConstant.RESPONSE_TOKEN;
        bean.mobile = TestConstant.PHONE_RIGHT_1;
        Bundle bundle = mock(Bundle.class);
        when(bean.toArgs()).thenReturn(bundle);

        mockStatic(RuntimeCache.class);

        presenter.perform(TestConstant.PHONE_RIGHT_1, captcha);
        verify(model).loginByCaptcha(eq(TestConstant.PHONE_RIGHT_1), eq(captcha), loginCaptor.capture());
        loginCaptor.getValue().onSuccess(loginResponse);
        verify(view).close(any(Bundle.class), anyString());

        verifyStatic(RuntimeCache.class, times(1));
        RuntimeCache.savePhone(eq(TestConstant.PHONE_RIGHT_1));
        RuntimeCache.saveToken(eq(TestConstant.RESPONSE_TOKEN));
    }

}