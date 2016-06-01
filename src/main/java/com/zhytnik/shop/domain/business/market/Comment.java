package com.zhytnik.shop.domain.business.market;

import com.zhytnik.shop.domain.DomainObject;

import javax.persistence.Entity;

/**
 * @author Alexey Zhytnik
 * @since 28.05.2016
 */
@Entity(name = "T_COMMENT")
public class Comment extends DomainObject {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
