package com.zhytnik.shop.domain.text;

import com.zhytnik.shop.domain.DomainObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * @author Alexey Zhytnik
 * @since 28.05.2016
 */
@Entity(name = "T_M_STR")
public class MultilanguageString extends DomainObject {

    @OneToMany(fetch = FetchType.LAZY)
    private Set<MultilanguageTranslation> translations;

    @Column(name = "CODE")
    private String code;

    public Set<MultilanguageTranslation> getTranslations() {
        return translations;
    }

    public void setTranslations(Set<MultilanguageTranslation> translations) {
        this.translations = translations;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
