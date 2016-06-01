package com.zhytnik.shop.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
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

    @Column(name = "CHANGE_DATE", nullable = false)
    private Date changeDate;

    public DomainObject() {
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
}
