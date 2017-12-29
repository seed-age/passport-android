package cc.seedland.inf.passport.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cc.seedland.inf.passport.TestConstant;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by xuchunlei on 2017/12/18.
 */
public class ValidateUtilTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void checkNull() throws Exception {
        assertEquals(ValidateUtil.checkNull(TestConstant.EMPTY_STRING), true);
        assertEquals(ValidateUtil.checkNull(TestConstant.NULL_STRING), true);
        assertEquals(ValidateUtil.checkNull(TestConstant.SPACE_STRING), true);

        assertSame(ValidateUtil.checkNull("    a   "), false);
    }

    @Test
    public void checkPhone() throws Exception {
        assertEquals(ValidateUtil.checkPhone(TestConstant.PHONE_RIGHT_1), Constant.ERROR_CODE_NONE);
        assertEquals(ValidateUtil.checkPhone(TestConstant.PHONE_RIGHT_2), Constant.ERROR_CODE_NONE);
        assertEquals(ValidateUtil.checkPhone(TestConstant.PHONE_WRONG_1), Constant.ERROR_CODE_PHONE_FORMAT);
        assertEquals(ValidateUtil.checkPhone(TestConstant.PHONE_WRONG_2), Constant.ERROR_CODE_PHONE_FORMAT);
        assertEquals(ValidateUtil.checkPhone(TestConstant.PHONE_WRONG_3), Constant.ERROR_CODE_PHONE_FORMAT);
        assertEquals(ValidateUtil.checkPhone(TestConstant.PHONE_WRONG_4), Constant.ERROR_CODE_PHONE_FORMAT);
        assertEquals(ValidateUtil.checkPhone(TestConstant.PHONE_WRONG_5), Constant.ERROR_CODE_PHONE_FORMAT);

        assertEquals(ValidateUtil.checkPhone(TestConstant.EMPTY_STRING), Constant.ERROR_CODE_PHONE_EMPTY);
        assertEquals(ValidateUtil.checkPhone(TestConstant.NULL_STRING), Constant.ERROR_CODE_PHONE_EMPTY);
        assertEquals(ValidateUtil.checkPhone(TestConstant.SPACE_STRING), Constant.ERROR_CODE_PHONE_EMPTY);
    }

    @Test
    public void checkCaptcha() throws Exception {
        assertEquals(ValidateUtil.checkCaptcha(TestConstant.EMPTY_STRING), Constant.ERROR_CODE_CAPTCHA_EMPTY);
        assertEquals(ValidateUtil.checkCaptcha(TestConstant.SPACE_STRING), Constant.ERROR_CODE_CAPTCHA_EMPTY);
        assertEquals(ValidateUtil.checkCaptcha(TestConstant.NULL_STRING), Constant.ERROR_CODE_CAPTCHA_EMPTY);

        assertEquals(ValidateUtil.checkCaptcha(TestConstant.CAPTCHA_RIGHT_1), Constant.ERROR_CODE_NONE);
        assertEquals(ValidateUtil.checkCaptcha(TestConstant.CAPTCHA_RIGHT_2), Constant.ERROR_CODE_NONE);
    }

    @Test
    public void checkPasswordConfirm() throws Exception {
        assertEquals(ValidateUtil.checkPasswordConfirm(TestConstant.SPACE_STRING, TestConstant.SPACE_STRING), Constant.ERROR_CODE_PASSWORD_EMPTY);
        assertEquals(ValidateUtil.checkPasswordConfirm(TestConstant.EMPTY_STRING, TestConstant.EMPTY_STRING), Constant.ERROR_CODE_PASSWORD_EMPTY);
        assertEquals(ValidateUtil.checkPasswordConfirm(TestConstant.NULL_STRING, TestConstant.NULL_STRING), Constant.ERROR_CODE_PASSWORD_EMPTY);

        assertEquals(ValidateUtil.checkPasswordConfirm(TestConstant.PASSWORD_WRONG_1, TestConstant.PASSWORD_WRONG_1), Constant.ERROR_CODE_PASSWORD_FORMAT);
        assertEquals(ValidateUtil.checkPasswordConfirm(TestConstant.PASSWORD_WRONG_2, TestConstant.PASSWORD_WRONG_2), Constant.ERROR_CODE_PASSWORD_FORMAT);

        assertEquals(ValidateUtil.checkPasswordConfirm(TestConstant.PASSWORD_RIGHT_1, TestConstant.PASSWORD_RIGHT_1), Constant.ERROR_CODE_NONE);
        assertEquals(ValidateUtil.checkPasswordConfirm(TestConstant.PASSWORD_RIGHT_1, TestConstant.PASSWORD_RIGHT_2), Constant.ERROR_CODE_NONE);

        assertEquals(ValidateUtil.checkPasswordConfirm(TestConstant.PASSWORD_RIGHT_1, TestConstant.PASSWORD_RIGHT_3), Constant.ERROR_CODE_PASSWORD_CONFIRM);

    }

    @Test
    public void checkPassword() throws Exception {

        assertEquals(ValidateUtil.checkPassword(TestConstant.EMPTY_STRING), Constant.ERROR_CODE_PASSWORD_EMPTY);
        assertEquals(ValidateUtil.checkPassword(TestConstant.SPACE_STRING), Constant.ERROR_CODE_PASSWORD_EMPTY);
        assertEquals(ValidateUtil.checkPassword(TestConstant.NULL_STRING), Constant.ERROR_CODE_PASSWORD_EMPTY);

        assertEquals(ValidateUtil.checkPassword(TestConstant.PASSWORD_WRONG_1), Constant.ERROR_CODE_PASSWORD_FORMAT);
        assertEquals(ValidateUtil.checkPassword(TestConstant.PASSWORD_WRONG_2), Constant.ERROR_CODE_PASSWORD_FORMAT);

        assertEquals(ValidateUtil.checkPassword(TestConstant.PASSWORD_RIGHT_1), Constant.ERROR_CODE_NONE);
        assertEquals(ValidateUtil.checkPassword(TestConstant.PASSWORD_RIGHT_2), Constant.ERROR_CODE_NONE);
        assertEquals(ValidateUtil.checkPassword(TestConstant.PASSWORD_RIGHT_3), Constant.ERROR_CODE_NONE);
    }

}