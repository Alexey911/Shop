package com.zhytnik.shop.datahelper;

import com.zhytnik.shop.domain.text.MultilanguageString;
import com.zhytnik.shop.domain.text.MultilanguageTranslation;
import com.zhytnik.shop.dto.MultiStringDto;

import java.util.Locale;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Collections.singletonMap;

/**
 * @author Alexey Zhytnik
 * @since 28.06.2016
 */
public class StringDH {

    private StringDH() {
    }

    public static MultilanguageString createString(){
        final MultilanguageString s = new MultilanguageString();
        final MultilanguageTranslation t = new MultilanguageTranslation();
        t.setDefault(true);
        t.setLanguage(Locale.ENGLISH);
        t.setTranslation("translation");
        s.setTranslations(newHashSet(t));
        return s;
    }

    public static MultiStringDto createString(Long id, Locale lang, String value) {
        final MultiStringDto str = new MultiStringDto();
        str.setId(id);
        str.setTranslations(singletonMap(lang, value));
        return str;
    }
}
