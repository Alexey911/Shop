package com.zhytnik.shop.domain.text;

import com.zhytnik.shop.domain.VersionableEntity;

import javax.persistence.*;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

/**
 * @author Alexey Zhytnik
 * @since 28.05.2016
 */
@Table(name = "T_STRING")
@Entity
public class MultilanguageString extends VersionableEntity {

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "TRANSLATIONS",
            joinColumns = @JoinColumn(name = "STRING_ID"),
            inverseJoinColumns = @JoinColumn(name = "TRANSLATION_ID"))
    private Set<MultilanguageTranslation> translations;

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
}
