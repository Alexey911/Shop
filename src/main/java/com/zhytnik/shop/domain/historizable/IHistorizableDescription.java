package com.zhytnik.shop.domain.historizable;

import com.zhytnik.shop.domain.IBasicEntity;

import java.util.Date;

/**
 * @author Alexey Zhytnik
 * @since 28.05.2016
 */
public interface IHistorizableDescription<HP extends IHistorizableObjectPointer> extends IBasicEntity {

    Date getValidFrom();

    void setValidFrom(Date validFrom);

    Date getValidTo();

    void setValidTo(Date validTo);

    HP getHistorizablePointer();

    void setHistorizablePointer(HP pointer);
}
