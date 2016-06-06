package com.zhytnik.shop.util;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import static com.zhytnik.shop.App.SETTINGS;
import static com.zhytnik.shop.util.DataSetVerifier.verify;

/**
 * @author Alexey Zhytnik
 * @since 05.06.2016
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(SETTINGS)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextBeforeModesTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        MethodExecutionListener.class,
        DbUnitTestExecutionListener.class})
public abstract class TransactionalTest {

    private static ThreadLocal<TestContext> testContext = new ThreadLocal<>();

    @AfterTransaction
    public void afterTransaction() throws Exception {
        final TestContext context = testContext.get();
        testContext.remove();
        verify(context);
    }

    static void setTestContext(TestContext testContext) {
        TransactionalTest.testContext.set(testContext);
    }
}
