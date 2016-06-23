package com.zhytnik.shop.service.support;

import com.zhytnik.shop.domain.IDomainObject;
import com.zhytnik.shop.domain.dynamic.IDynamicEntity;
import com.zhytnik.shop.domain.text.MultilanguageString;

import static com.zhytnik.shop.backend.access.DynamicAccessUtil.getDynamicValues;

/**
 * @author Alexey Zhytnik
 * @since 23.06.2016
 */
public class ClientSupportManager {

    private MultiLanguageStringManager stringManager = new MultiLanguageStringManager();

    public void prepareBeforeSend(IDomainObject object) {
        if (isMultiLanguageString(object)) {
            stringManager.updateTranslations((MultilanguageString) object);
        }
    }

    public void prepareBeforeSend(IDynamicEntity entity) {
        for (Object field : getDynamicValues(entity)) {
            if (isMultiLanguageString(field)) {
                stringManager.updateTranslations((MultilanguageString) field);
            }
        }
    }

    public void prepareBeforeSave(IDomainObject object) {
        if (isMultiLanguageString(object)) {
            stringManager.setDefaultTranslation((MultilanguageString) object);
        }
    }

    public void prepareBeforeSave(IDynamicEntity entity) {
        for (Object field : getDynamicValues(entity)) {
            if (isMultiLanguageString(field)) {
                stringManager.setDefaultTranslation((MultilanguageString) field);
            }
        }
    }

    private boolean isMultiLanguageString(Object object) {
        return object != null && object.getClass().equals(MultilanguageString.class);
    }
}
