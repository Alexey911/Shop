package com.zhytnik.shop.util;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import static com.zhytnik.shop.util.TransactionalTest.checkExpectedDataSet;

/**
 * @author Alexey Zhytnik
 * @since 07.06.2016
 */
class ExpectedDataSetListener extends AbstractTestExecutionListener {

    @Override
    public void afterTestMethod(TestContext context) {
        throwIfContainsExceptions(context);
        checkExpectedDataSet(context);
    }

    private void throwIfContainsExceptions(TestContext context) {
        final Throwable t = context.getTestException();
        if (t != null) throw new RuntimeException(t);
    }
}
