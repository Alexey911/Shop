package com.zhytnik.shop.domain;

/**
 * @author Alexey Zhytnik
 * @since 28.05.2016
 */
public interface IBasicEntity extends IDomainObject {

    boolean getDeleteFlag();

    void setDeleteFlag(boolean deleteFlag);
}
