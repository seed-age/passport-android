package cc.seedland.inf.passport.login;

import android.content.res.Resources;
import android.support.test.runner.AndroidJUnit4;
import android.test.mock.MockContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import cc.seedland.inf.passport.TestConstant;
import cc.seedland.inf.passport.common.LoginBean;
import cc.seedland.inf.passport.network.BizCallback;
import cc.seedland.inf.passport.util.Constant;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by xuchunlei on 2017/12/18.
 */

@RunWith(AndroidJUnit4.class)
public class LoginPasswordPresenterTest {

    private LoginPasswordPresenter presenter;

    @Mock
    private LoginModel model;
    @Mock
    private ILoginMainView view;
    @Captor
    private ArgumentCaptor<BizCallback<LoginBean>> callbackCaptor;

    @Mock
    private MockContext appContext;
    @Mock
    private Resources resources;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(appContext.getResources()).thenReturn(resources);
        when(resources.getString(anyInt())).thenReturn(TestConstant.FAKE_STRING);

        Constant.APP_CONTEXT = appContext;
        presenter = new LoginPasswordPresenter(model);
        presenter.attach(view);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void perform() throws Exception {
        presenter.perform(TestConstant.EMPTY_STRING, TestConstant.PASSWORD_STRING);
        verify(model, times(0)).loginByPassword(anyString(), anyString(), callbackCaptor.capture());

        presenter.perform(TestConstant.NULL_STRING, TestConstant.PASSWORD_STRING);
        verify(model, times(0)).loginByPassword(anyString(), anyString(), callbackCaptor.capture());

        verify(view, times(2)).showToast(eq(TestConstant.FAKE_STRING));
    }

    @Test
    public void refreshPhone() throws Exception {
    }

}