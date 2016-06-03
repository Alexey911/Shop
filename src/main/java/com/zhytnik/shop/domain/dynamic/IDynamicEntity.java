package com.zhytnik.shop.domain.dynamic;

import com.zhytnik.shop.backend.access.DynamicAccessor;
import com.zhytnik.shop.domain.IBasicEntity;

/**
 * @author Alexey Zhytnik
 * @since 28.05.2016
 */
public interface IDynamicEntity extends IBasicEntity {

    DynamicType getDynamicType();

    void setDynamicType(DynamicType type);

    @Deprecated
    Object[] getDynamicFieldsValues();

    @Deprecated
    void setDynamicFieldsValues(Object[] values);

    DynamicAccessor getDynamicAccessor();

    void setDynamicAccessor(DynamicAccessor accessor);
}
