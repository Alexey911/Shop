package com.zhytnik.shop.datahelper;

import com.zhytnik.shop.domain.text.MultilanguageString;
import com.zhytnik.shop.domain.text.MultilanguageTranslation;

import java.util.Locale;

import static com.google.common.collect.Sets.newHashSet;

/**
 * @author Alexey Zhytnik
 * @since 28.06.2016
 */
public class MultiStringDH {

    private MultiStringDH() {
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
}
