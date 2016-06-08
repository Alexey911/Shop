package com.zhytnik.shop.domain.text;

import com.zhytnik.shop.domain.VersionableEntity;

import javax.persistence.*;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

/**
 * @author Alexey Zhytnik
 * @since 28.05.2016
 */
@Entity(name = "T_MULTI_STR")
public class MultilanguageString extends VersionableEntity {

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<MultilanguageTranslation> translations;

    @Column(name = "CODE")
    private String code;

    public Set<MultilanguageTranslation> getTranslations() {
        return translations;
    }

    public void add(MultilanguageTranslation translation) {
        if (translations == null) translations = newHashSet();
        translations.add(translation);
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
