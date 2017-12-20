package cc.seedland.inf.passport.network;

import org.junit.Before;
import org.junit.Test;

import cc.seedland.inf.passport.TestConstant;
import cc.seedland.inf.passport.util.Constant;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

/**
 * Created by xuchunlei on 2017/12/19.
 */
public class RuntimeCacheTest {

    @Before
    public void setup() throws Exception {
        Constant.APP_CONTEXT = getInstrumentation().getTargetContext();
    }

    @Test
    public void token() throws Exception {

        RuntimeCache.saveToken(TestConstant.RESPONSE_TOKEN);
        assertEquals(RuntimeCache.getToken(), TestConstant.RESPONSE_TOKEN);
    }

    @Test
    public void phone() throws Exception {
        RuntimeCache.savePhone(TestConstant.PHONE_RIGHT_1);
        assertEquals(RuntimeCache.getPhone(), TestConstant.PHONE_RIGHT_1);
    }

}