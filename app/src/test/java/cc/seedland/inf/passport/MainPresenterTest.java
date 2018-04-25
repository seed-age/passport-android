package cc.seedland.inf.passport;

import org.junit.Before;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by xuchunlei on 2017/12/13.
 */
public class MainPresenterTest {

    MainPresenter presenter;

    @Before
    public void setup() {
        RxUnitTestTools.openRxTools();
        mock(MainPresenter.class);

    }

}