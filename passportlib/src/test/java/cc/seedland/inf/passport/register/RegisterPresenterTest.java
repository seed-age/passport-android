package cc.seedland.inf.passport.register;

import android.os.Bundle;

import com.lzy.okgo.model.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.TestConstant;
import cc.seedland.inf.passport.common.ICaptchaView;
import cc.seedland.inf.passport.network.BizCallback;
import cc.seedland.inf.passport.util.Constant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Created by xuchunlei on 2018/1/2.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ RegisterPresenter.class, Constant.class, Response.class })
public class RegisterPresenterTest {

    private RegisterPresenter presenter;
    @Mock
    private RegisterModel model;
    @Mock
    private ICaptchaView view;

    @Before
    public void setUp() throws Exception {

        whenNew(RegisterModel.class).withNoArguments().thenReturn(model);

        presenter = new RegisterPresenter();
        presenter.attach(view);

        mockStatic(Constant.class);
        when(Constant.getString(R.string.error_phone_format)).thenReturn("phone error");
        when(Constant.getString(R.string.error_captcha_empty)).thenReturn("captcha empty error");
        when(Constant.getString(R.string.error_password_format)).thenReturn("password format error");
        when(Constant.getString(R.string.error_password_confirm)).thenReturn("password confirm error");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void performCaptcha() throws Exception {
        // 失败 - 手机号格式错误
        presenter.performCaptcha(TestConstant.PHONE_WRONG_1);
        verify(view).showToast(eq("phone error"));
        verify(model, never()).getCaptcha(anyString(), any(BizCallback.class));

        // 成功
        ArgumentCaptor<BizCallback<BaseBean>> captor = ArgumentCaptor.forClass(BizCallback.class);
        Response<BaseBean> response = mock(Response.class);
        presenter.performCaptcha(TestConstant.PHONE_RIGHT_1);
        verify(model, only()).getCaptcha(eq(TestConstant.PHONE_RIGHT_1), captor.capture());
        captor.getValue().onSuccess(response);
        verify(view).startWaitingCaptcha();

    }

    @Test
    public void performRegister() throws Exception {

        // 失败 - 手机号格式错误
        presenter.performRegister(TestConstant.PHONE_WRONG_1, TestConstant.CAPTCHA_RIGHT_1, TestConstant.PASSWORD_RIGHT_1, TestConstant.PASSWORD_RIGHT_1);
        verify(view).showToast(eq("phone error"));
        verify(model, never()).performPhone(anyString(), anyString(), anyString(), any(BizCallback.class));

        // 失败 - 验证码错误
        presenter.performRegister(TestConstant.PHONE_RIGHT_1, TestConstant.EMPTY_STRING, TestConstant.PASSWORD_RIGHT_1, TestConstant.PASSWORD_RIGHT_1);
        verify(view).showToast(eq("captcha empty error"));
        verify(model, never()).performPhone(anyString(), anyString(), anyString(), any(BizCallback.class));

        // 失败 - 密码格式错误
        presenter.performRegister(TestConstant.PHONE_RIGHT_1, TestConstant.CAPTCHA_RIGHT_1, TestConstant.PASSWORD_WRONG_1, TestConstant.PASSWORD_RIGHT_1);
        verify(view).showToast(eq("password format error"));
        verify(model, never()).performPhone(anyString(), anyString(), anyString(), any(BizCallback.class));

        // 失败 - 密码前后两次输入不一致
        presenter.performRegister(TestConstant.PHONE_RIGHT_1, TestConstant.CAPTCHA_RIGHT_1, TestConstant.PASSWORD_RIGHT_1, TestConstant.PASSWORD_RIGHT_3);
        verify(view).showToast(eq("password confirm error"));
        verify(model, never()).performPhone(anyString(), anyString(), anyString(), any(BizCallback.class));

        // 成功 - 带有空格
        ArgumentCaptor<BizCallback<RegisterBean>> captor = ArgumentCaptor.forClass(BizCallback.class);
        Response<RegisterBean> response = mock(Response.class);
        RegisterBean bean = mock(RegisterBean.class);
        when(response.body()).thenReturn(bean);
        Bundle args = mock(Bundle.class);
        when(bean.toArgs()).thenReturn(args);
        presenter.performRegister(TestConstant.PHONE_RIGHT_2, TestConstant.CAPTCHA_RIGHT_2, TestConstant.PASSWORD_RIGHT_1, TestConstant.PASSWORD_RIGHT_2);
        verify(model, only()).performPhone(eq(TestConstant.PHONE_RIGHT_2.trim()), eq(TestConstant.PASSWORD_RIGHT_1.trim()),  eq(TestConstant.CAPTCHA_RIGHT_2.trim()), captor.capture());
        captor.getValue().onSuccess(response);
        verify(response).body();
        verify(view).close(eq(args), anyString());
    }

}