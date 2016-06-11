package com.zhytnik.shop.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Alexey Zhytnik
 * @since 28.05.2016
 */
@MappedSuperclass
public class DomainObject implements IDomainObject {

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "LAST_CHANGE", nullable = false)
    private Date changeDate;

    public DomainObject() {
        if (DomainObjectUtil.getInstance() != null) {
            id = DomainObjectUtil.getInstance().getNextId();
        }
    }

    @PrePersist
    @PreUpdate
    void updateChangeDate() {
        changeDate = new Date();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Date getChangeDate() {
        return changeDate;
    }

    @Override
    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    @Override
    public String toString() {
        return new StringBuilder().
                append("DomainObject[class=").append(this.getClass().getSimpleName()).
                append(", ID=").append(id).append("]").toString();
    }
}
