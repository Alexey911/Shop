package com.zhytnik.shop.assertion;

import com.zhytnik.shop.domain.IDomainObject;
import com.zhytnik.shop.exception.InfrastructureException;
import com.zhytnik.shop.exception.NotFoundException;
import com.zhytnik.shop.exception.ValidationException;

/**
 * @author Alexey Zhytnik
 * @since 18.06.2016
 */
public class CommonAssert {

    private CommonAssert(){
    }

    public static void notEmptyName(String name){
        if (name == null) throw new InfrastructureException("empty.name");
    }

    public static void notEmptyId(Long id){
        if (id == null) throw new ValidationException("empty.id.field");
    }

    public static void shouldBeFoundById(IDomainObject object, Long id){
        if (object == null) throw new NotFoundException("not.found.by.id", id);
    }
}
