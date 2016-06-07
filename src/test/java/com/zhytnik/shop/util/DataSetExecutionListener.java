package com.zhytnik.shop.util;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import static com.zhytnik.shop.backend.tool.SessionUtil.resetCache;
import static com.zhytnik.shop.util.dataset.DataSetUtil.hasDataSet;
import static com.zhytnik.shop.util.dataset.DataSetUtil.installDataSet;

/**
 * @author Alexey Zhytnik
 * @since 05.06.2016
 */
class DataSetExecutionListener extends AbstractTestExecutionListener {

    @Override
    public void beforeTestMethod(TestContext context) throws Exception {
        if (hasDataSet(context)) {
            installDataSet(context);
            resetCache();
        }
    }
}
