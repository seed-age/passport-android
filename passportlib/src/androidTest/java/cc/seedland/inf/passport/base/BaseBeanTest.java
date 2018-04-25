package cc.seedland.inf.passport.base;

import android.os.Bundle;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by xuchunlei on 2018/1/2.
 */

public class BaseBeanTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void toArgs() throws Exception {
        TestBean bean = new TestBean();
        bean.pubStrA = "pub-str-a";
        bean.pubIntA = 1;
        bean.proStrA = "pro-str-a";
        bean.proIntA = 2;
        Bundle args = bean.toArgs();

        assertEquals(args.size(), 2);
        assertTrue(args.containsKey("pubStrA"));
        assertTrue(args.containsKey("pubIntA"));

        assertEquals(bean.pubStrA, args.getString("pubStrA"));
        assertEquals(bean.pubIntA, args.getInt("pubIntA"));

    }

    @Test
    public void toStringTest() throws Exception {

        BaseBean bean = new BaseBean();
        bean.raw = "raw data";
        assertEquals(bean.toString(), bean.raw);
    }

    private static class TestBean extends BaseBean {
        public String pubStrA;
        public int pubIntA;
        protected String proStrA;
        protected int proIntA;
    }

}