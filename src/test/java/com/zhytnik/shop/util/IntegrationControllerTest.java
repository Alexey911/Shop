package com.zhytnik.shop.util;

import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * @author Alexey Zhytnik
 * @since 13.06.2016
 */
@WebAppConfiguration
@ContextConfiguration("classpath:/context/test-web-context.xml")
public abstract class IntegrationControllerTest extends TransactionalTest {

    protected MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(getController()).
                setValidator(new LocalValidatorFactoryBean()).build();
    }

    protected abstract Object getController();
}
