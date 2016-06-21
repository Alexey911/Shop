package com.zhytnik.shop.dto.core.converter.impl;

import com.zhytnik.shop.domain.text.MultilanguageString;
import com.zhytnik.shop.domain.text.MultilanguageTranslation;
import com.zhytnik.shop.dto.MultiStringDto;
import com.zhytnik.shop.dto.core.converter.IEntityConverter;

import java.util.Locale;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

/**
 * @author Alexey Zhytnik
 * @since 21.06.2016
 */
class MultiStringConverter implements IEntityConverter<MultilanguageString, MultiStringDto> {

    @Override
    public MultiStringDto convert(MultilanguageString str) {
        final MultiStringDto dto = new MultiStringDto();
        dto.setId(str.getId());
        dto.setTranslations(getConvertedTranslations(str));
        return dto;
    }

    private Map<Locale, String> getConvertedTranslations(MultilanguageString s) {
        final Map<Locale, String> translations = newHashMap();
        for (MultilanguageTranslation t : s.getTranslations()) {
            translations.put(t.getLanguage(), t.getTranslation());
        }
        return translations;
    }
}
