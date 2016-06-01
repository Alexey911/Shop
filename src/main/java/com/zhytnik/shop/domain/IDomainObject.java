package com.zhytnik.shop.domain;

import java.util.Date;

/**
 * @author Alexey Zhytnik
 * @since 28.05.2016
 */
public interface IDomainObject {

    Long getId();

    void setId(Long id);

    Date getChangeDate();

    void setChangeDate(Date changeDate);
}
