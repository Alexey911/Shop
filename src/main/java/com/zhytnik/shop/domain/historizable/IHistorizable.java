package com.zhytnik.shop.domain.historizable;

/**
 * @author Alexey Zhytnik
 * @since 28.05.2016
 */
public interface IHistorizable<HP extends IHistorizableObjectPointer> {

    Long getCode();

    void setCode(Long code);

    IHistorizableDescription<HP> getHistorizableDescription();

    void setHistorizableDescription(IHistorizableDescription<HP> description);
}
