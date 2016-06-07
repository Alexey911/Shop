package com.zhytnik.shop.backend.tool;

import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.testing.UnitTest;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.Oracle10gDialect;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.List;

import static com.zhytnik.shop.backend.tool.TypeUtil.getSqlType;
import static com.zhytnik.shop.domain.dynamic.DynamicType.DYNAMIC_ID_FIELD;
import static com.zhytnik.shop.domain.dynamic.PrimitiveType.LONG;
import static com.zhytnik.shop.domain.dynamic.PrimitiveType.STRING;
import static java.text.MessageFormat.format;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Alexey Zhytnik
 * @since 04.06.2016
 */
@Category(UnitTest.class)
public class TableScriptGeneratorTest {

    static final String TABLE_NAME = "table1";
    static final String CUSTOM_FIELD = "field1";

    TableScriptGenerator generator = new TableScriptGenerator();

    Dialect dialect = new Oracle10gDialect();

    DynamicField field = new DynamicField();

    List<DynamicField> fields = singletonList(field);

    @Before
    public void setUp() {
        generator.setDialect(dialect);

        field.setOrder(0);
        field.setPrimitiveType(STRING);
        field.setName(CUSTOM_FIELD);
    }

    @Test
    public void generates() {
        generator.generate(TABLE_NAME, fields);
    }

    @Test
    public void shouldGenerateValidScript() {
        final String expected = format("create table {0} ({1} {2}, {3} {4} not null)",
                TABLE_NAME,
                CUSTOM_FIELD, getSqlType(dialect, STRING),
                DYNAMIC_ID_FIELD, getSqlType(dialect, LONG));
        assertThat(generator.generate(TABLE_NAME, fields)).isEqualTo(expected);
    }
}
