package cc.seedland.inf.passport.network;

import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.base.Request;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.ref.WeakReference;

import cc.seedland.inf.passport.base.IPassportView;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by xuchunlei on 2017/12/16.
 */
@RunWith(PowerMockRunner.class)
public class BizCallbackTest {

    @Mock
    private IPassportView view;
    @Mock
    private Request<BaseBean, PostRequest<BaseBean>> request;

    private Response<BaseBean> response;

    private BizCallback<BaseBean> callback;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        response = new Response<>();
        callback = new BizCallback<>(BaseBean.class, new WeakReference<>(view));
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void onStart() throws Exception {
        callback.onStart(request);
        verify(view).showLoading();
    }

    @Test
    public void onError() throws Exception {

        // 未知错误
        callback.onError(response);
        verify(view).showToast(eq("unknown error"));

        response.setException(new Throwable("error"));
        callback.onError(response);
        verify(view).showToast(eq("error"));

    }

    @Test
    public void onFinish() throws Exception {
        callback.onFinish();
        verify(view).hideLoading();
    }

}