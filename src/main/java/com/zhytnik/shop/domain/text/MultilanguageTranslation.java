package com.zhytnik.shop.domain.text;

import com.zhytnik.shop.domain.DomainObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Locale;

/**
 * @author Alexey Zhytnik
 * @since 28.05.2016
 */
@Table(name = "T_TRANSLATION")
@Entity
public class MultilanguageTranslation extends DomainObject {

    @Column(name = "LANGUAGE", nullable = false)
    private Locale language;

    @Column(name = "TRANSLATION", nullable = false, length = 200)
    private String translation;

    @Column(name = "DEFAULT_VALUE", nullable = false)
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
