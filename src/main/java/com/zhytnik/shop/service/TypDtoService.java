package com.zhytnik.shop.service;

import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.dto.TypeDto;
import com.zhytnik.shop.dto.core.converter.IDtoConverter;
import com.zhytnik.shop.dto.core.converter.IEntityConverter;
import com.zhytnik.shop.service.support.ClientSupportManager;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author Alexey Zhytnik
 * @since 13.06.2016
 */
public class TypDtoService {

    private TypeService service;
    private ClientSupportManager clientManager;

    private IDtoConverter<DynamicType, TypeDto> dtoConverter;
    private IEntityConverter<DynamicType, TypeDto> typeConverter;

    public TypeDto findById(Long id) {
        return prepareAndConvert(service.findById(id));
    }

    public List<TypeDto> loadAll() {
        final List<TypeDto> dtos = newArrayList();
        for (DynamicType type : service.loadAll())
            dtos.add(prepareAndConvert(type));
        return dtos;
    }

    private TypeDto prepareAndConvert(DynamicType type) {
        for (DynamicField field : type.getFields()) {
            clientManager.prepareBeforeSend(field.getDescription());
        }
        return typeConverter.convert(type);
    }

    public boolean isUniqueName(String name) {
        return service.isUniqueName(name);
    }

    public Long create(TypeDto dto) {
        final DynamicType type = dtoConverter.convert(dto);
        for (DynamicField field : type.getFields()) {
            clientManager.prepareBeforeSave(field.getDescription());
        }
        return service.create(type);
    }

    public void remove(Long id) {
        service.remove(id);
    }

    public void update(TypeDto dto) {
        service.update(dtoConverter.convert(dto));
    }

    public void setClientSupportManager(ClientSupportManager clientSupportManager) {
        clientManager = clientSupportManager;
    }

    public void setService(TypeService service) {
        this.service = service;
    }

    public void setTypeConverter(IEntityConverter<DynamicType, TypeDto> typeConverter) {
        this.typeConverter = typeConverter;
    }

    public void setDtoConverter(IDtoConverter<DynamicType, TypeDto> dtoConverter) {
        this.dtoConverter = dtoConverter;
    }
}
