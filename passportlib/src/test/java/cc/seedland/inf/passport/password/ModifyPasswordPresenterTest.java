package cc.seedland.inf.passport.password;

import android.os.Bundle;

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
import cc.seedland.inf.passport.base.IBaseView;
import cc.seedland.inf.passport.common.LoginBean;
import cc.seedland.inf.passport.network.ApiUtil;
import cc.seedland.inf.passport.network.BizCallback;
import cc.seedland.inf.passport.network.RuntimeCache;
import cc.seedland.inf.passport.util.Constant;
import cc.seedland.inf.passport.util.DeviceUtil;
import cc.seedland.inf.passport.util.ValidateUtil;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Created by xuchunlei on 2017/12/28.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest( { Constant.class, RuntimeCache.class, DeviceUtil.class, ModifyPasswordPresenter.class, Response.class })
public class ModifyPasswordPresenterTest {

    @Mock
    private IBaseView view;
    @Mock
    private PasswordModel model;

    private ModifyPasswordPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        whenNew(PasswordModel.class).withNoArguments().thenReturn(model);

        presenter = new ModifyPasswordPresenter();
        presenter.attach(view);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void performModify() throws Exception {

        String errorFormat = "password format error";
        String errorConfirm = "password confirm error";

        mockStatic(Constant.class);
        when(Constant.getString(Constant.ERROR_CODE_PASSWORD_FORMAT)).thenReturn(errorFormat);
        when(Constant.getString(Constant.ERROR_CODE_PASSWORD_CONFIRM)).thenReturn(errorConfirm);

        mockStatic(RuntimeCache.class);
        when(RuntimeCache.getToken()).thenReturn(TestConstant.RESPONSE_TOKEN);
        mockStatic(DeviceUtil.class);

        // 失败 - 原密码格式错误
        presenter.performModify(TestConstant.PASSWORD_WRONG_1, TestConstant.PASSWORD_RIGHT_1, TestConstant.PASSWORD_RIGHT_1);
        verify(view).showToast(eq(errorFormat));
        verify(model, times(0)).modify(eq(TestConstant.PASSWORD_WRONG_1), eq(TestConstant.PASSWORD_RIGHT_1), any(BizCallback.class));

        // 失败 - 新密码两次输入不一致
        presenter.performModify(TestConstant.PASSWORD_RIGHT_1, TestConstant.PASSWORD_RIGHT_2, TestConstant.PASSWORD_RIGHT_3);
        verify(view).showToast(eq(errorConfirm));
        verify(model, times(0)).modify(eq(TestConstant.PASSWORD_RIGHT_1), eq(TestConstant.PASSWORD_RIGHT_1), any(BizCallback.class));

        // 失败 - 原密码与新密码一致
        presenter.performModify(TestConstant.PASSWORD_RIGHT_1, TestConstant.PASSWORD_RIGHT_1, TestConstant.PASSWORD_RIGHT_1);
        verify(model, times(0)).modify(eq(TestConstant.PASSWORD_RIGHT_1), eq(TestConstant.PASSWORD_RIGHT_1), any(BizCallback.class));

        // 成功
        ArgumentCaptor<BizCallback<LoginBean>> captor = ArgumentCaptor.forClass(BizCallback.class);
        presenter.performModify(TestConstant.PASSWORD_RIGHT_1, TestConstant.PASSWORD_RIGHT_3, TestConstant.PASSWORD_RIGHT_3);
        verify(model).modify(eq(TestConstant.PASSWORD_RIGHT_1), eq(TestConstant.PASSWORD_RIGHT_3.trim()), captor.capture());
        // 成功 - mock response
        Response<LoginBean> response = mock(Response.class);
        LoginBean bean = mock(LoginBean.class);
        when(response.body()).thenReturn(bean);
        bean.token = TestConstant.RESPONSE_TOKEN;
        bean.raw = "raw data";
        Bundle args = mock(Bundle.class);
        when(bean.toArgs()).thenReturn(args);
        // 成功 - verify response
        captor.getValue().onSuccess(response);
        verify(view).close(any(Bundle.class), anyString());
        verifyStatic(RuntimeCache.class);
        RuntimeCache.saveToken(eq(TestConstant.RESPONSE_TOKEN));

    }

}