package com.zhytnik.shop.service;

import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.dto.TypeDto;
import com.zhytnik.shop.dto.core.converter.IDtoConverter;
import com.zhytnik.shop.dto.core.converter.IEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author Alexey Zhytnik
 * @since 13.06.2016
 */
@Component
public class TypDtoService {

    @Autowired
    private TypeService service;

    @Autowired
    private IEntityConverter<DynamicType, TypeDto> typeConverter;

    @Autowired
    private IDtoConverter<DynamicType, TypeDto> dtoConverter;

    public TypeDto findById(Long id) {
        return typeConverter.convert(service.findById(id));
    }

    public List<TypeDto> loadAll() {
        final List<TypeDto> dtos = newArrayList();
        for (DynamicType type : service.loadAll()) {
            dtos.add(typeConverter.convert(type));
        }
        return dtos;
    }

    public boolean isUniqueName(String name) {
        return service.isUniqueName(name);
    }

    public void create(TypeDto dto) {
        service.create(dtoConverter.convert(dto));
    }

    public void remove(Long id) {
        service.remove(id);
    }

    public void update(TypeDto dto) {
        final DynamicType type = dtoConverter.convert(dto);
        final DynamicType persisted = service.findById(dto.getId());
        copy(persisted, type);
        service.update(type);
    }

    private void copy(DynamicType from, DynamicType to) {
        to.setName(from.getName());
        to.setFields(from.getFields());
    }
}
