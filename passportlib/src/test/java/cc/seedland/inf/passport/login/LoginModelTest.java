package cc.seedland.inf.passport.login;

import android.content.SharedPreferences;
import android.test.mock.MockContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.HttpURLConnection;

import cc.seedland.inf.passport.TestConstant;
import cc.seedland.inf.passport.common.LoginBean;
import cc.seedland.inf.passport.network.ApiUtil;
import cc.seedland.inf.passport.network.BizCallback;
import cc.seedland.inf.passport.util.Constant;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by xuchunlei on 2017/12/14.
 */
public class LoginModelTest {

    private LoginModel model;

//    private MockWebServer server;

    @Mock
    private BizCallback<LoginBean> callback;
    @Mock
    private MockContext context;
    @Mock
    private SharedPreferences prefs;
    @Mock
    private SharedPreferences.Editor editor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        model = new LoginModel();

//        server = new MockWebServer();
//
//        Constant.APP_CONTEXT = context;
//        when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(prefs);
//        when(prefs.edit()).thenReturn(editor);
//        ApiUtil.init(anyString(), anyString(), server.getHostName());
    }

    @After
    public void tearDown() throws Exception {
//        server.shutdown();
    }

    @Test
    public void loginByPassword() throws Exception {

        // 登录成功
//        MockResponse rightResponse = new MockResponse()
//                .addHeader("Content-Type", TestConstant.HEADER_CONTENT_TYPE_TEST)
//                .setResponseCode(HttpURLConnection.HTTP_OK)
//                .setBody(TestConstant.LOGIN_RESPONSE_BODY_RIGHT);
//        server.enqueue(rightResponse);

//        final Response<LoginBean> rightLoginResponse = new Response<>();
//        LoginBean bean = new LoginBean();
//        bean.uid = 100;
//        bean.mobile = "13800138000";
//        bean.nickname = "User_13800138000";
//        bean.raw = TestConstant.LOGIN_RESPONSE_BODY_RIGHT_TEST;
//        bean.token = "WVr5CyKwtEH1wAjXPG7AlNDZRb9FKvnAVhB8ppliGVnGpaTHryWoBgoiv56kMy1Nd9cC3N7ufASX7C3BoEXh/Wy8XYI7InM4e3RN6PUccHxejHy5LJiuk3cOrkDMyl22vO7tEu9UmKUJ5bTWkeOg2Nz+gpoeNA9u+rfPIRjh3gQ=";
//        rightLoginResponse.setBody(bean);


//        Executors.newSingleThreadExecutor().execute(new Runnable() {
//            @Override
//            public void run() {
//                model.loginByPassword(TestConstant.PHONE_RIGHT_1_TEST, TestConstant.PASSWORD_RIGHT_1_TEST, callback);
//            }
//        });
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        verify(callback).onFinish();


//        doAnswer(new Answer() {
//            @Override
//            public Void answer(InvocationOnMock invocation) throws Throwable {
//
//                return null;
//            }
//        }).when(callback).onSuccess(any(Response.class));



    }

    @Test
    public void loginByCaptcha() throws Exception {
    }

    @Test
    public void obtainCaptcha() throws Exception {
    }

}