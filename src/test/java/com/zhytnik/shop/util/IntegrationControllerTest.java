package com.zhytnik.shop.util;

import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

/**
 * @author Alexey Zhytnik
 * @since 13.06.2016
 */
@WebAppConfiguration
public abstract class IntegrationControllerTest extends TransactionalTest {

    @Resource
    private WebApplicationContext webContext;

    protected MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }
}
