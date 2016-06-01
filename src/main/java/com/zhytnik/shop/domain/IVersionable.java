package com.zhytnik.shop.domain;

/**
 * @author Alexey Zhytnik
 * @since 28.05.2016
 */
public interface IVersionable {

    Long getVersion();

    void setVersion(Long version);
}
