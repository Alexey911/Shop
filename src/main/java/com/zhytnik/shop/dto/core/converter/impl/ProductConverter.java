package com.zhytnik.shop.dto.core.converter.impl;

import com.zhytnik.shop.domain.market.product.Product;
import com.zhytnik.shop.domain.text.MultilanguageString;
import com.zhytnik.shop.dto.MultiStringDto;
import com.zhytnik.shop.dto.ProductDto;
import com.zhytnik.shop.dto.core.converter.IEntityConverter;

import java.util.Map;

import static com.zhytnik.shop.backend.access.DynamicAccessUtil.getDynamicValues;

/**
 * @author Alexey Zhytnik
 * @since 22.06.2016
 */
class ProductConverter implements IEntityConverter<Product, ProductDto> {

    private DynamicConverter dynamicConverter;
    private IEntityConverter<MultilanguageString, MultiStringDto> stringConverter;

    @Override
    public ProductDto convert(Product product) {
        final ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setShortName(product.getShortName());
        dto.setType(product.getDynamicType().getId());
        dto.setTitle(stringConverter.convert(product.getTitle()));
        dto.setDescription(stringConverter.convert(product.getDescription()));
        convertDynamic(product, dto);
        return dto;
    }

    private void convertDynamic(Product product, ProductDto dto) {
        final Object[] values = getDynamicValues(product);
        final Map<String, Object> fields = dynamicConverter.convert(values, product.getDynamicType());
        dto.setDynamicFields(fields);
    }

    public void setDynamicConverter(DynamicConverter dynamicConverter) {
        this.dynamicConverter = dynamicConverter;
    }

    public void setStringConverter(IEntityConverter<MultilanguageString, MultiStringDto> stringConverter) {
        this.stringConverter = stringConverter;
    }
}
