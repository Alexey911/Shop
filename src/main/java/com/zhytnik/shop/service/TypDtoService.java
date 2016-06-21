package com.zhytnik.shop.service;

import com.zhytnik.shop.domain.dynamic.DynamicField;
import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.domain.text.MultilanguageString;
import com.zhytnik.shop.domain.text.MultilanguageTranslation;
import com.zhytnik.shop.dto.TypeDto;
import com.zhytnik.shop.dto.core.converter.IDtoConverter;
import com.zhytnik.shop.dto.core.converter.IEntityConverter;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import static com.google.common.collect.Iterables.getOnlyElement;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.singleton;

/**
 * @author Alexey Zhytnik
 * @since 13.06.2016
 */
public class TypDtoService {

    private TypeService service;

    private IDtoConverter<DynamicType, TypeDto> dtoConverter;
    private IEntityConverter<DynamicType, TypeDto> typeConverter;

    public TypeDto findById(Long id) {
        return convert(service.findById(id));
    }

    public List<TypeDto> loadAll() {
        final List<TypeDto> dtos = newArrayList();
        for (DynamicType type : service.loadAll())
            dtos.add(convert(type));
        return dtos;
    }

    private TypeDto convert(DynamicType type) {
        removeExcessTranslations(type);
        return typeConverter.convert(type);
    }

    private void removeExcessTranslations(DynamicType type) {
        for (DynamicField field : type.getFields()) {
            final MultilanguageString desc = field.getDescription();
            desc.setTranslations(singleton(getGeneralTranslation(desc)));
        }
    }

    private MultilanguageTranslation getGeneralTranslation(MultilanguageString str) {
        final Set<MultilanguageTranslation> translations = str.getTranslations();
        if (translations.size() == 1) return getOnlyElement(translations);

        final Locale currLocale = LocaleContextHolder.getLocale();
        MultilanguageTranslation defaultTranslation = null;

        for (MultilanguageTranslation t : translations) {
            if (t.isDefault()) defaultTranslation = t;
            if (t.getLanguage().equals(currLocale))
                return t;
        }
        return defaultTranslation;
    }

    public boolean isUniqueName(String name) {
        return service.isUniqueName(name);
    }

    public Long create(TypeDto dto) {
        final DynamicType type = dtoConverter.convert(dto);
        for (DynamicField field : type.getFields()) {
            setDefaultTranslation(field);
        }
        return service.create(type);
    }

    private void setDefaultTranslation(DynamicField field) {
        final Locale currLocale = LocaleContextHolder.getLocale();
        final Set<MultilanguageTranslation> translations = field.getDescription().getTranslations();

        for (MultilanguageTranslation t : translations) {
            if (t.getLanguage().equals(currLocale)) {
                t.setDefault(true);
                return;
            }
        }
        getOnlyElement(translations).setDefault(true);
    }

    public void remove(Long id) {
        service.remove(id);
    }

    public void update(TypeDto dto) {
        service.update(dtoConverter.convert(dto));
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
