package com.zhytnik.shop.dto.core.converter.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhytnik.shop.domain.market.product.Product;
import com.zhytnik.shop.domain.text.MultilanguageString;
import com.zhytnik.shop.dto.MultiStringDto;
import com.zhytnik.shop.dto.ProductDto;
import com.zhytnik.shop.dto.core.Dto;
import com.zhytnik.shop.dto.core.converter.IDtoConverter;
import com.zhytnik.shop.exception.InfrastructureException;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import static com.zhytnik.shop.backend.access.DynamicAccessUtil.getDynamicValues;
import static com.zhytnik.shop.backend.access.DynamicAccessUtil.setDynamicValues;
import static com.zhytnik.shop.dto.core.converter.impl.DtoManager.convertDto;

/**
 * @author Alexey Zhytnik
 * @since 22.06.2016
 */
class ProductDtoConverter implements IDtoConverter<Product, ProductDto> {

    private TypeManager typeManager;

    private IDtoConverter<MultilanguageString, MultiStringDto> stringDtoConverter;

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public Product convert(ProductDto dto) {
        final Product product = new Product();
        typeManager.initialType(product, dto.getType());
        product.setShortName(dto.getShortName());
        product.setTitle(stringDtoConverter.convert(dto.getTitle()));
        product.setDescription(stringDtoConverter.convert(dto.getDescription()));
        setDynamicValues(product, convertDynamic(loadDefinition(product), getDynamicValues(product), dto));
        return product;
    }

    private Map<String, Entry<Integer, Class<?>>> loadDefinition(Product product) {
        return typeManager.getTypeDescription(product);
    }

    private Object[] convertDynamic(Map<String, Entry<Integer, Class<?>>> definition,
                                    Object[] values, ProductDto dto) {
        for (Entry<String, Object> field : dto.getDynamicFields().entrySet()) {
            final Entry<Integer, Class<?>> desc = definition.get(field.getKey());
            values[desc.getKey()] = convert(field.getValue(), desc.getValue());
        }
        return values;
    }

    private Object convert(Object rowField, Class<?> type) {
        if (DtoManager.isPrimitive(type)) return rowField;
        if (type.equals(Date.class)) return new Date(Long.valueOf(rowField.toString()));
        return convertComplexDto(rowField, type);
    }

    @SuppressWarnings("unchecked")
    private Object convertComplexDto(Object rowField, Class<?> type) {
        Dto field;
        try {
            field = (Dto) mapper.convertValue(rowField, type);
        } catch (IllegalArgumentException e) {
            throw new InfrastructureException(e);
        }
        return convertDto(field, (Class<? extends Dto>) type);
    }

    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public void setTypeManager(TypeManager typeManager) {
        this.typeManager = typeManager;
    }

    public void setStringDtoConverter(IDtoConverter<MultilanguageString, MultiStringDto> stringDtoConverter) {
        this.stringDtoConverter = stringDtoConverter;
    }
}