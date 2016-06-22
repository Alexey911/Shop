package com.zhytnik.shop.dto.core.converter.impl;

import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.domain.market.product.Product;
import com.zhytnik.shop.dto.core.Dto;
import com.zhytnik.shop.service.TypeService;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;
import static com.zhytnik.shop.backend.access.DynamicAccessUtil.initialDynamicValues;
import static com.zhytnik.shop.dto.core.converter.impl.DtoUtil.getDtoClass;

/**
 * @author Alexey Zhytnik
 * @since 22.06.2016
 */
//TODO: merge with DynamicAccessor
class TypeManager {

    private TypeService service;

    public void initialType(Product product, Long typeId) {
        //TODO: using type cache
        final DynamicType type = service.findById(typeId);
        product.setDynamicType(type);
        initialDynamicValues(product, type.getFields().size());
    }

    @SuppressWarnings("unchecked")
    public Map<String, Map.Entry<Integer, Class<? extends Dto>>> getTypeDescription(Product product) {
        final DynamicType type = product.getDynamicType();
        final List<DynamicField> fields = type.getFields();

        final Map<String, Map.Entry<Integer, Class<? extends Dto>>> description = newHashMap();
        for (DynamicField field : fields) {
            final Integer order = field.getOrder();
            final Class<? extends Dto> clazz = getDtoClass(field.getPrimitiveType());
            description.put(field.getName(), new AbstractMap.SimpleEntry(order, clazz));
        }
        return description;
    }

    public void setTypeService(TypeService service) {
        this.service = service;
    }
}
