package cc.seedland.inf.passport.network;

import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.base.Request;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.ref.WeakReference;

import cc.seedland.inf.passport.base.BaseBean;
import cc.seedland.inf.passport.base.IBaseView;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

/**
 * Created by xuchunlei on 2017/12/16.
 */
public class BizCallbackTest {

    @Mock
    private IBaseView view;
    @Mock
    private Request<BaseBean, PostRequest<BaseBean>> request;

    private Response<BaseBean> response;

    private BizCallback<BaseBean> callback;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        callback = new BizCallback<>(BaseBean.class, new WeakReference<>(view));
        response = new Response<>();
        response.setBody(new BaseBean());
        response.setException(new Throwable("error"));
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
        callback.onError(response);
        verify(view).showToast(anyString());
    }

    @Test
    public void onFinish() throws Exception {
        callback.onFinish();
        verify(view).hideLoading();
    }

}