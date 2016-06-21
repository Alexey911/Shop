package com.zhytnik.shop.dto;

import com.zhytnik.shop.dto.core.Dto;
import com.zhytnik.shop.dto.core.Identity;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Locale;
import java.util.Map;

/**
 * @author Alexey Zhytnik
 * @since 21.06.2016
 */
public class MultiStringDto implements Dto, Identity {

    private Long id;

    @NotEmpty
    private Map<Locale, String> translations;

    public Map<Locale, String> getTranslations() {
        return translations;
    }

    public void setTranslations(Map<Locale, String> translations) {
        this.translations = translations;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
