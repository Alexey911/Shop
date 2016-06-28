package com.zhytnik.shop.datahelper;

import com.zhytnik.shop.dto.MultiStringDto;
import com.zhytnik.shop.dto.ProductDto;

import java.util.Locale;

import static java.util.Collections.singletonMap;

/**
 * @author Alexey Zhytnik
 * @since 28.06.2016
 */
public class ProductDtoDH {

    private ProductDtoDH() {
    }

    public static ProductDto createProductDto() {
        final ProductDto dto = new ProductDto();
        dto.setType(45L);
        dto.setShortName("short name");
        dto.setId(4L);
        dto.setDescription(createString(2L, Locale.CANADA, "description"));
        dto.setTitle(createString(3L, Locale.ENGLISH, "title"));
        return dto;
    }

    private static MultiStringDto createString(Long id, Locale lang, String value) {
        final MultiStringDto str = new MultiStringDto();
        str.setId(id);
        str.setTranslations(singletonMap(lang, value));
        return str;
    }
}
