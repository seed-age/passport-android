package cc.seedland.inf.passport.login;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.test.mock.MockContext;

import com.lzy.okgo.request.PostRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import cc.seedland.inf.passport.TestConstant;
import cc.seedland.inf.passport.common.LoginBean;
import cc.seedland.inf.passport.network.BizCallback;
import cc.seedland.inf.passport.util.Constant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by xuchunlei on 2017/12/14.
 */
public class LoginPasswordPresenterTest {

    @Captor
    private ArgumentCaptor<BizCallback<LoginBean>> callbackCaptor;

    @Mock
    private LoginModel model;
    @Mock
    private ILoginMainView view;
    @Mock
    private MockContext context;
    @Mock
    private SharedPreferences prefs;
    @Mock
    private SharedPreferences.Editor editor;

    private LoginPasswordPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Constant.APP_CONTEXT = context;
        when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(prefs);
        when(prefs.edit()).thenReturn(editor);

        presenter = new LoginPasswordPresenter(model);
        presenter.attach(view);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void perform() throws Exception {

        // 正确
        presenter.perform(TestConstant.PHONE_RIGHT_1, TestConstant.PASSWORD_RIGHT_1);
        verify(model).loginByPassword(eq(TestConstant.PHONE_RIGHT_1), eq(TestConstant.PASSWORD_RIGHT_1), callbackCaptor.capture());

        callbackCaptor.getValue().onSuccess(TestConstant.LOGIN_RESPONSE);
        LoginBean bean = TestConstant.LOGIN_RESPONSE.body();
        verify(view).close(any(Bundle.class), eq(bean.raw));

        // 错误 - 手机号错误
        presenter.perform(TestConstant.PHONE_WRONG_1, anyString());
        verify(model, times(0)).loginByPassword(eq(TestConstant.PHONE_WRONG_1), anyString(), callbackCaptor.capture());

        // 错误 - 密码错误
        presenter.perform(anyString(), TestConstant.PASSWORD_WRONG_1);
        verify(model, times(0)).loginByPassword(anyString(), eq(TestConstant.PASSWORD_WRONG_1), callbackCaptor.capture());

    }

    @Test
    public void refreshPhone() throws Exception {
        String info = "message";

        // 正确
        presenter.refreshPhone(TestConstant.PHONE_RIGHT_1, info);
        verify(view).loadPhone(TestConstant.PHONE_RIGHT_1);

        // 错误 - 手机号码不正确
        presenter.refreshPhone(TestConstant.PHONE_WRONG_1, info);
        verify(view, times(0)).loadPhone(TestConstant.PHONE_WRONG_1);

        verify(view, times(2)).showTip(info);
    }

}