package com.zhytnik.shop.dto.core.converter.impl;

import com.zhytnik.shop.domain.market.product.Product;
import com.zhytnik.shop.domain.text.MultilanguageString;
import com.zhytnik.shop.domain.text.MultilanguageTranslation;
import com.zhytnik.shop.dto.MultiStringDto;
import com.zhytnik.shop.dto.ProductDto;
import com.zhytnik.shop.testing.UnitTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static com.zhytnik.shop.datahelper.ProductDH.createProduct;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Alexey Zhytnik
 * @since 28.06.2016
 */
@Category(UnitTest.class)
public class ProductConverterTest {

    ProductConverter converter = new ProductConverter();

    Product product;

    @Before
    public void setUp() {
        product = createProduct();
        converter.setStringConverter(new MultiStringConverter());
        converter.setDynamicConverter(new DynamicConverter());
    }

    @Test
    public void converts() {
        final ProductDto dto = converter.convert(product);

        checkString(dto.getTitle(), product.getTitle());
        assertThat(dto.getId()).isEqualTo(product.getId());
        checkString(dto.getDescription(), product.getDescription());
        assertThat(dto.getShortName()).isEqualTo(product.getShortName());
        assertThat(dto.getType()).isEqualTo(product.getDynamicType().getId());

        assertThat(dto.getDynamicFields())
                .hasSameSizeAs(product.getDynamicFieldsValues())
                .doesNotContainEntry("field", 45L);
    }

    void checkString(MultiStringDto dto, MultilanguageString str) {
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(str.getId());
        assertThat(dto.getTranslations()).hasSameSizeAs(str.getTranslations());
        for (MultilanguageTranslation t : str.getTranslations()) {
            assertThat(dto.getTranslations()).
                    containsEntry(t.getLanguage(), t.getTranslation());
        }
    }
}
