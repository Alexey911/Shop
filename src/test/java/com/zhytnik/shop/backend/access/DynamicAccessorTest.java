package com.zhytnik.shop.backend.access;

import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.PrimitiveType;
import com.zhytnik.shop.domain.market.product.Product;
import com.zhytnik.shop.exeception.InfrastructureException;
import org.junit.Before;
import org.junit.Test;

import static com.zhytnik.shop.datahelper.ProductDH.createProduct;
import static com.zhytnik.shop.domain.dynamic.PrimitiveType.STRING;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Alexey Zhytnik
 * @since 04.06.2016
 */
public class DynamicAccessorTest {

    static final String FIELD_NAME = "custom_field";
    static final PrimitiveType FIELD_TYPE = STRING;

    DynamicAccessor accessor;

    @Before
    public void setUp() {
        final Product product = createProduct();
        final DynamicField field = product.getDynamicType().getFields().get(0);
        field.setName(FIELD_NAME);
        field.setType(FIELD_TYPE);

        accessor = product.getDynamicAccessor();
    }

    @Test
    public void writesFields() {
        accessor.set(FIELD_NAME, "new value");
    }

    @Test
    public void readsFields() {
        accessor.get(FIELD_NAME);
    }

    @Test(expected = InfrastructureException.class)
    public void checksTypes() {
        accessor.set(FIELD_NAME, 5);
    }

    @Test(expected = InfrastructureException.class)
    public void checksFieldExistingInCaseWriting() {
        accessor.set("Nonexistent field", 5);
    }

    @Test(expected = InfrastructureException.class)
    public void checksFieldExistingInCaseReading() {
        accessor.get("Nonexistent field");
    }

    @Test
    public void containsChanges() {
        final int changesBefore = accessor.getChangesFieldIds().size();
        accessor.set(FIELD_NAME, "new value");
        assertThat(accessor.getChangesFieldIds()).hasSize(changesBefore + 1);
    }

    @Test
    public void updateDynamicFields() {
        final String newValue = "some value";
        accessor.set(FIELD_NAME, newValue);
        assertThat(accessor.get(FIELD_NAME)).isEqualTo(newValue);
    }
}
