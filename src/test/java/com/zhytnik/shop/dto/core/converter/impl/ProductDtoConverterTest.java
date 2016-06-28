package com.zhytnik.shop.dto.core.converter.impl;

import com.zhytnik.shop.backend.repository.TypeRepository;
import com.zhytnik.shop.backend.tool.ProductCreator;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.domain.market.product.Product;
import com.zhytnik.shop.domain.text.MultilanguageString;
import com.zhytnik.shop.domain.text.MultilanguageTranslation;
import com.zhytnik.shop.dto.MultiStringDto;
import com.zhytnik.shop.dto.ProductDto;
import com.zhytnik.shop.testing.UnitTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Locale;
import java.util.Map.Entry;

import static com.google.common.collect.Iterables.getOnlyElement;
import static com.zhytnik.shop.datahelper.DynamicTypeDH.createTypeWithSingleField;
import static com.zhytnik.shop.datahelper.ProductDtoDH.createProductDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Alexey Zhytnik
 * @since 28.06.2016
 */
@Category(UnitTest.class)
public class ProductDtoConverterTest {

    static final String DYNAMIC_FIELD = "field";

    ProductDtoConverter converter = new ProductDtoConverter();

    ProductDto dto = createProductDto();
    DynamicType type = createTypeWithSingleField();

    @Before
    public void setUp() {
        dto.setDynamicField(DYNAMIC_FIELD, 3L);
        converter.setStringDtoConverter(new MultiStringDtoConverter());
        converter.setDynamicDtoConverter(new DynamicDtoConverter());
        converter.setProductCreator(getProductCreator(type));
    }

    @Test
    public void converts() {
        final Product product = converter.convert(dto);
        assertThat(product.getId()).isEqualTo(dto.getId());
        assertThat(product.getDynamicType().getId()).isEqualTo(dto.getType());
        assertThat(product.getShortName()).isEqualTo(dto.getShortName());
        checkString(product.getTitle(), dto.getTitle());
        checkString(product.getDescription(), dto.getDescription());
        assertThat(product.getDynamicAccessor().get("field")).isEqualTo(3L);
    }

    ProductCreator getProductCreator(DynamicType type) {
        final ProductCreator creator = new ProductCreator();
        final TypeRepository repository = mock(TypeRepository.class);
        creator.setTypeRepository(repository);
        when(repository.findOne(type.getId())).thenReturn(type);
        return creator;
    }

    void checkString(MultilanguageString str, MultiStringDto dto) {
        assertThat(str).isNotNull();
        assertThat(str.getId()).isEqualTo(dto.getId());
        assertThat(str.getTranslations()).hasSameSizeAs(dto.getTranslations().entrySet()).hasSize(1);

        final MultilanguageTranslation t = getOnlyElement(str.getTranslations());
        final Entry<Locale, String> tDto = getOnlyElement(dto.getTranslations().entrySet());

        assertThat(t.getTranslation()).isEqualTo(tDto.getValue());
        assertThat(t.getLanguage()).isEqualTo(tDto.getKey());
    }
}
