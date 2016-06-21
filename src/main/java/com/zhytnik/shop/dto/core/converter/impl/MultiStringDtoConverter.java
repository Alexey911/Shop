package com.zhytnik.shop.dto.core.converter.impl;

import com.zhytnik.shop.domain.text.MultilanguageString;
import com.zhytnik.shop.domain.text.MultilanguageTranslation;
import com.zhytnik.shop.dto.MultiStringDto;
import com.zhytnik.shop.dto.core.converter.IDtoConverter;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static com.zhytnik.shop.dto.core.converter.impl.DtoUtil.mergeIdentity;

/**
 * @author Alexey Zhytnik
 * @since 21.06.2016
 */
class MultiStringDtoConverter implements IDtoConverter<MultilanguageString, MultiStringDto> {

    @Override
    public MultilanguageString convert(MultiStringDto dto) {
        final MultilanguageString str = new MultilanguageString();
        mergeIdentity(str, dto);
        str.setTranslations(getTranslations(dto));
        return str;
    }

    private Set<MultilanguageTranslation> getTranslations(MultiStringDto dto) {
        final Map<Locale, String> dtos = dto.getTranslations();
        final Set<MultilanguageTranslation> translations = newHashSet();

        for (Map.Entry<Locale, String> pair : dtos.entrySet()) {
            final MultilanguageTranslation t = new MultilanguageTranslation();
            t.setLanguage(pair.getKey());
            t.setTranslation(pair.getValue());
            translations.add(t);
        }
        return translations;
    }
}
