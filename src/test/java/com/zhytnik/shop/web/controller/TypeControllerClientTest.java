package com.zhytnik.shop.web.controller;

import com.zhytnik.shop.testing.IntegrationTest;
import com.zhytnik.shop.util.IntegrationControllerTest;
import com.zhytnik.shop.util.dataset.DataSet;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * @author Alexey Zhytnik
 * @since 13.06.2016
 */
@DataSet
@Category(IntegrationTest.class)
public class TypeControllerClientTest extends IntegrationControllerTest {

    final static long EXIST_TYPE = 5L;

    @Autowired
    TypeController typeController;

    @Test
    public void findsById() throws Exception {
        mockMvc.perform(get("/types/{id}", EXIST_TYPE)).
                andExpect(jsonPath("id", is((int) EXIST_TYPE))).
                andDo(print());
    }
}
