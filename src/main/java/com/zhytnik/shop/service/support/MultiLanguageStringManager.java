package com.zhytnik.shop.service.support;

import com.zhytnik.shop.domain.text.MultilanguageString;
import com.zhytnik.shop.domain.text.MultilanguageTranslation;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;
import java.util.Set;

import static com.google.common.collect.Iterables.getOnlyElement;
import static java.util.Collections.singleton;

/**
 * @author Alexey Zhytnik
 * @since 23.06.2016
 */
class MultiLanguageStringManager {

    public void setDefaultTranslation(MultilanguageString s) {
        if (s == null) return;
        final Locale currLocale = LocaleContextHolder.getLocale();
        final Set<MultilanguageTranslation> translations = s.getTranslations();

        if (translations.size() == 1) {
            getOnlyElement(translations).setDefault(true);
            return;
        }
        for (MultilanguageTranslation t : translations) {
            if (t.getLanguage().equals(currLocale)) {
                t.setDefault(true);
                return;
            }
        }
    }

    public void updateTranslations(MultilanguageString s) {
        if (s == null) return;
        final Set<MultilanguageTranslation> translations = s.getTranslations();
        if (translations.size() == 1) return;

        final Locale currLocale = LocaleContextHolder.getLocale();
        MultilanguageTranslation translation = null;

        for (MultilanguageTranslation t : translations) {
            if (t.isDefault() || t.getLanguage().equals(currLocale)) {
                translation = t;
                break;
            }
        }
        s.setTranslations(singleton(translation));
    }
}
