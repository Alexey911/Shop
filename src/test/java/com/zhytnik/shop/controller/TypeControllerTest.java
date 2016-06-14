package com.zhytnik.shop.controller;

import com.zhytnik.shop.dto.TypeDto;
import com.zhytnik.shop.service.TypDtoService;
import com.zhytnik.shop.testing.UnitTest;
import com.zhytnik.shop.util.ControllerTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.zhytnik.shop.util.web.WebUtil.*;
import static java.util.Collections.singletonList;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Alexey Zhytnik
 * @since 13.06.2016
 */
@Category(UnitTest.class)
public class TypeControllerTest extends ControllerTest {

    static final long EXIST_TYPE = 5L;

    @InjectMocks
    TypeController controller;

    @Mock
    TypDtoService service;

    TypeDto type = new TypeDto();

    @Override
    public void setUp() {
        super.setUp();
        type.setId(EXIST_TYPE);
        when(service.findById(EXIST_TYPE)).thenReturn(type);
        when(service.loadAll()).thenReturn(singletonList(type));
    }

    @Test
    public void loadsById() throws Exception {
        mockMvc.perform(get("/types/{id}", EXIST_TYPE)).
                andExpect(jsonPath("id", is((int) EXIST_TYPE))).
                andDo(print());
    }

    @Test
    public void removes() throws Exception {
        mockMvc.perform(delete("/types/{id}", EXIST_TYPE)).
                andExpect(status().isOk()).
                andDo(print());
    }

    @Test
    public void checksFreeNames() throws Exception {
        mockMvc.perform(get("/types?isFree={name}", "some name")).
                andExpect(content("false")).
                andDo(print());
    }

    @Test
    public void loadsAll() throws Exception {
        mockMvc.perform(get("/types").accept(APPLICATION_JSON)).
                andExpect(jsonPath("$[0].id", is((int) EXIST_TYPE))).
                andDo(print());
    }

    @Test
    public void creates() throws Exception {
        mockMvc.perform(post("/types").contentType(APPLICATION_JSON_UTF8).
                content(convertToJson(type))).
                andExpect(content(EXIST_TYPE)).
                andDo(print());
    }

    @Test
    public void updates() throws Exception {
        mockMvc.perform(put("/types").contentType(APPLICATION_JSON_UTF8).
                content(convertToJson(type))).
                andExpect(status().isOk()).
                andDo(print());
    }

    @Override
    protected Object getController() {
        return controller;
    }
}
