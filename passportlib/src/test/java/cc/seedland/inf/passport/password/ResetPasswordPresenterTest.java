package cc.seedland.inf.passport.password;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.test.mock.MockContext;

import com.lzy.okgo.model.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.TestConstant;
import cc.seedland.inf.passport.common.ICaptchaView;
import cc.seedland.inf.passport.common.SimpleBean;
import cc.seedland.inf.passport.network.BizCallback;
import cc.seedland.inf.passport.util.Constant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Created by xuchunlei on 2017/12/29.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ResetPasswordPresenter.class, Constant.class, Response.class })
public class ResetPasswordPresenterTest {

    private ResetPasswordPresenter presenter;

    @Mock
    private PasswordModel model;
    @Mock
    private ICaptchaView view;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        whenNew(PasswordModel.class).withNoArguments().thenReturn(model);

        Constant.APP_CONTEXT = mock(MockContext.class);
        SharedPreferences prefs = mock(SharedPreferences.class);
        PowerMockito.when(Constant.APP_CONTEXT.getSharedPreferences(anyString(), anyInt())).thenReturn(prefs);
        mockStatic(Constant.class);
        when(Constant.getString(R.string.error_phone_format)).thenReturn("phone error");
        when(Constant.getString(R.string.error_password_format)).thenReturn("password format error");
        when(Constant.getString(R.string.error_password_confirm)).thenReturn("password confirm error");
        when(Constant.getString(R.string.error_captcha_empty)).thenReturn("captcha error");

        presenter = new ResetPasswordPresenter();
        presenter.attach(view);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void performCaptcha() throws Exception {

        // 失败 - 手机号码
        presenter.performCaptcha(TestConstant.PHONE_WRONG_1);
        verify(model, never()).obtainCaptcha(anyString(), any(BizCallback.class));
        verify(view, never()).startWaitingCaptcha();
        verify(view).showToast(eq("phone error"));

        // 成功

        ArgumentCaptor<BizCallback<BaseBean>> captor = ArgumentCaptor.forClass(BizCallback.class);
        Response<BaseBean> response = mock(Response.class);

        presenter.performCaptcha(TestConstant.PHONE_RIGHT_2);
        verify(model).obtainCaptcha(eq(TestConstant.PHONE_RIGHT_2.trim()), captor.capture());
        captor.getValue().onSuccess(response);
        verify(view).startWaitingCaptcha();
    }

    @Test
    public void performReset() throws Exception {

        String captcha = "1234";

        // 失败 - 手机错误
        presenter.performReset(TestConstant.PHONE_WRONG_1, TestConstant.PASSWORD_RIGHT_1, TestConstant.PASSWORD_RIGHT_2, anyString());
        verify(view).showToast(eq("phone error"));
        verify(model, never()).reset(anyString(), anyString(), anyString(), any(BizCallback.class));

        // 失败 - 密码格式错误
        presenter.performReset(TestConstant.PHONE_RIGHT_1, TestConstant.PASSWORD_WRONG_1, TestConstant.PASSWORD_WRONG_1, captcha);
        verify(view).showToast(eq("password format error"));
        verify(model, never()).reset(anyString(), anyString(), anyString(), any(BizCallback.class));

        // 失败 - 密码不一致
        presenter.performReset(TestConstant.PHONE_RIGHT_1, TestConstant.PASSWORD_RIGHT_1, TestConstant.PASSWORD_RIGHT_3, captcha);
        verify(view).showToast(eq("password confirm error"));
        verify(model, never()).reset(anyString(), anyString(), anyString(), any(BizCallback.class));

        // 失败 - 验证码错误
        presenter.performReset(TestConstant.PHONE_RIGHT_1, TestConstant.PASSWORD_RIGHT_1, TestConstant.PASSWORD_RIGHT_3, TestConstant.EMPTY_STRING);
        verify(view).showToast(eq("captcha error"));
        verify(model, never()).reset(anyString(), anyString(), anyString(), any(BizCallback.class));

        // 成功 - 输入带有空格
        ArgumentCaptor<BizCallback<SimpleBean>> captor = ArgumentCaptor.forClass(BizCallback.class);
        Response<SimpleBean> response = mock(Response.class);
        SimpleBean bean = mock(SimpleBean.class);
        when(response.body()).thenReturn(bean);
        Bundle args = mock(Bundle.class);
        when(bean.toArgs()).thenReturn(args);

        presenter.performReset(TestConstant.PHONE_RIGHT_2, TestConstant.PASSWORD_RIGHT_2, TestConstant.PASSWORD_RIGHT_2, captcha);
        verify(model, only()).reset(eq(TestConstant.PHONE_RIGHT_2.trim()), eq(TestConstant.PASSWORD_RIGHT_2.trim()), eq(captcha.trim()), captor.capture());
        captor.getValue().onSuccess(response);
        verify(view).close(eq(args), anyString());
    }

}