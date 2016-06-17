package com.zhytnik.shop.web.controller;

import com.zhytnik.shop.dto.FieldDto;
import com.zhytnik.shop.dto.TypeDto;
import com.zhytnik.shop.testing.IntegrationTest;
import com.zhytnik.shop.util.IntegrationControllerTest;
import com.zhytnik.shop.util.dataset.ClearSchema;
import com.zhytnik.shop.util.dataset.DataSet;
import com.zhytnik.shop.util.dataset.DropTable;
import com.zhytnik.shop.util.dataset.ExpectedDataSet;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static com.zhytnik.shop.domain.dynamic.PrimitiveType.LONG;
import static com.zhytnik.shop.domain.dynamic.PrimitiveType.STRING;
import static com.zhytnik.shop.util.dataset.DropTable.Phase.AFTER;
import static com.zhytnik.shop.util.web.WebUtil.*;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Alexey Zhytnik
 * @since 13.06.2016
 */
@DataSet
@Category(IntegrationTest.class)
public class TypeControllerClientTest extends IntegrationControllerTest {

    final static long EXIST_TYPE = 5L;
    final static String TYPE_NAME = "type_1";

    @Autowired
    TypeController typeController;

    TypeDto type = new TypeDto();
    FieldDto field = new FieldDto();

    @Override
    public void setUp() {
        super.setUp();

        field.setName("field1");
        field.setType(STRING);
        field.setOrder(0);

        type.setId(EXIST_TYPE);
        type.setName(TYPE_NAME);
        type.setFields(singletonList(field));
    }

    @Test
    public void searchesById() throws Exception {
        mockMvc.perform(get("/types/{id}", EXIST_TYPE)).
                andExpect(jsonPath("id", is((int) EXIST_TYPE))).
                andDo(print());
    }

    @Test
    @ClearSchema
    public void failsSearchIfNotExist() throws Exception {
        mockMvc.perform(get("/types/{id}", 456L)).
                andExpect(status().isNotFound()).
                andExpect(status().reason(notNullValue())).
                andDo(print());
    }

    @Test
    @ClearSchema
    @DropTable(tables = TYPE_NAME)
    @ExpectedDataSet("create")
    public void creates() throws Exception {
        mockMvc.perform(post("/types").contentType(APPLICATION_JSON_UTF8).
                content(convertToJson(type))).
                andExpect(content(EXIST_TYPE)).
                andDo(print());
    }

    @Test
    @ClearSchema
    public void validatesType() throws Exception {
        type.setName(" ");
        mockMvc.perform(post("/types").contentType(APPLICATION_JSON_UTF8).
                content(convertToJson(type))).
                andExpect(status().isBadRequest()).
                andDo(print());
    }

    @Test
    public void loadsAll() throws Exception {
        mockMvc.perform(get("/types")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$[0].id", is((int) EXIST_TYPE))).
                andDo(print());
    }

    @Test
    public void remembersExistingTypeNames() throws Exception {
        mockMvc.perform(get("/types?isFree={name}", TYPE_NAME)).
                andExpect(status().isOk()).
                andExpect(content(false)).
                andDo(print());
    }

    @Test
    public void checksFreeNames() throws Exception {
        mockMvc.perform(get("/types?isFree={name}", "not exist name")).
                andExpect(status().isOk()).
                andExpect(content(true)).
                andDo(print());
    }

    @Test
    @Commit
    @SqlGroup(@Sql("type.sql"))
    public void removesExistTypes() throws Exception {
        mockMvc.perform(delete("/types/{id}", EXIST_TYPE)).
                andExpect(status().isOk()).
                andDo(print());
        assertThat(typeController.loadAll()).isEmpty();
    }

    @Test
    @ClearSchema
    public void failsRemoveIfNotExist() throws Exception {
        mockMvc.perform(delete("/types/{id}", 789L)).
                andExpect(status().isNotFound()).
                andDo(print());
    }

    @Test
    @SqlGroup(@Sql("type.sql"))
    @ExpectedDataSet("update")
    @DropTable(tables = TYPE_NAME, phases = AFTER)
    public void updates() throws Exception {
        final TypeDto type = typeController.findById(EXIST_TYPE);
        type.getFields().get(0).setType(LONG);

        mockMvc.perform(put("/types/{id}", EXIST_TYPE).contentType(APPLICATION_JSON_UTF8).
                content(convertToJson(type))).
                andExpect(status().isOk()).
        andDo(print());
    }
}
