package com.zhytnik.shop.domain.dynamic;

import com.zhytnik.shop.domain.DomainObject;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;

/**
 * @author Alexey Zhytnik
 * @since 28.05.2016
 */
@Table(name = "T_TYPE")
@Entity
public class DynamicType extends DomainObject {

    public static final String DYNAMIC_ID_FIELD = "ID";

    @NotEmpty
    @Column(name = "NAME", unique = true)
    private String name;

    @NotEmpty
    @OneToMany(mappedBy = "type", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<DynamicField> fields;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DynamicField> getFields() {
        return fields;
    }

    public void setFields(List<DynamicField> fields) {
        this.fields = fields;
    }
}
