package com.zhytnik.shop.backend.tool;

import com.zhytnik.shop.domain.dynamic.DynamicField;
import org.hibernate.dialect.Dialect;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Table;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.zhytnik.shop.backend.tool.TypeUtil.getSqlType;
import static com.zhytnik.shop.domain.dynamic.DynamicType.DYNAMIC_ID_FIELD;
import static com.zhytnik.shop.domain.dynamic.PrimitiveType.LONG;

/**
 * @author Alexey Zhytnik
 * @since 26.05.2016
 */
@Component
class TableScriptGenerator {

    private Dialect dialect;

    private String schema;

    public String generate(String tableName, List<DynamicField> fields) {
        final Table table = new Table(tableName);

        for (DynamicField field : fields) {
            final Column column = createColumn(field);
            table.addColumn(column);
        }
        table.addColumn(generateIdColumn());
        return generateScript(table);
    }

    private Column generateIdColumn() {
        final Column column = new Column(DYNAMIC_ID_FIELD);
        column.setSqlType(getSqlType(dialect, LONG));
        column.setUnique(true);
        column.setNullable(false);
        return column;
    }

    private Column createColumn(DynamicField field) {
        final Column column = new Column(field.getName());
        column.setSqlType(getSqlType(dialect, field.getType()));
        column.setNullable(!field.isRequired());
        return column;
    }

    private String generateScript(Table table) {
        return table.sqlCreateString(dialect, null, null, schema);
    }

    public void setDialect(Dialect dialect) {
        this.dialect = dialect;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }
}
