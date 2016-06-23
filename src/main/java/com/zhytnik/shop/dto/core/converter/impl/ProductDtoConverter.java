package com.zhytnik.shop.dto.core.converter.impl;

import com.zhytnik.shop.backend.tool.ProductCreator;
import com.zhytnik.shop.domain.market.product.Product;
import com.zhytnik.shop.domain.text.MultilanguageString;
import com.zhytnik.shop.dto.MultiStringDto;
import com.zhytnik.shop.dto.ProductDto;
import com.zhytnik.shop.dto.core.converter.IDtoConverter;

import static com.zhytnik.shop.backend.access.DynamicAccessUtil.setDynamicValues;

/**
 * @author Alexey Zhytnik
 * @since 22.06.2016
 */
class ProductDtoConverter implements IDtoConverter<Product, ProductDto> {

    private ProductCreator creator;
    private DynamicDtoConverter dynamicDtoConverter;
    private IDtoConverter<MultilanguageString, MultiStringDto> stringDtoConverter;

    @Override
    public Product convert(ProductDto dto) {
        final Product product = creator.createByType(dto.getType());
        product.setShortName(dto.getShortName());
        product.setTitle(stringDtoConverter.convert(dto.getTitle()));
        product.setDescription(stringDtoConverter.convert(dto.getDescription()));
        convertDynamic(dto, product);
        return product;
    }

    private void convertDynamic(ProductDto dto, Product product) {
        final Object[] values = dynamicDtoConverter.convert(dto.getDynamicFields(), product.getDynamicType());
        setDynamicValues(product, values);
    }

    public void setProductCreator(ProductCreator productCreator) {
        creator = productCreator;
    }

    public void setDynamicDtoConverter(DynamicDtoConverter dynamicDtoConverter) {
        this.dynamicDtoConverter = dynamicDtoConverter;
    }

    public void setStringDtoConverter(IDtoConverter<MultilanguageString, MultiStringDto> stringDtoConverter) {
        this.stringDtoConverter = stringDtoConverter;
    }
}
