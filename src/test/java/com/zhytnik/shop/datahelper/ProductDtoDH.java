package com.zhytnik.shop.datahelper;

import com.zhytnik.shop.dto.ProductDto;

import java.util.Locale;

import static com.zhytnik.shop.datahelper.StringDH.createString;

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
}
