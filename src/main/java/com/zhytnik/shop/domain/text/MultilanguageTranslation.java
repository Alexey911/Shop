package com.zhytnik.shop.domain.text;

import com.zhytnik.shop.domain.DomainObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Locale;

/**
 * @author Alexey Zhytnik
 * @since 28.05.2016
 */
@Entity(name = "T_M_TR")
public class MultilanguageTranslation extends DomainObject {

    @Column(name = "LANGUAGE", nullable = false)
    private Locale language;

    @Column(name = "TRANSLATION", nullable = false, length = 200)
    private String translation;

    @Column(name = "IS_DEFAULT", nullable = false)
    private boolean defaultValue;

    public Locale getLanguage() {
        return language;
    }

    public void setLanguage(Locale language) {
        this.language = language;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public boolean isDefault() {
        return defaultValue;
    }

    public void setDefault(boolean isDefault) {
        defaultValue = isDefault;
    }
}
