package com.zhytnik.shop.datahelper;

import com.zhytnik.shop.domain.dynamic.DynamicType;
import com.zhytnik.shop.domain.market.Comment;
import com.zhytnik.shop.domain.market.product.Product;
import com.zhytnik.shop.domain.text.MultilanguageString;
import com.zhytnik.shop.domain.text.MultilanguageTranslation;

import static com.google.common.collect.Sets.newHashSet;
import static com.zhytnik.shop.datahelper.DynamicTypeDH.createTypeWithSingleField;
import static java.util.Locale.ENGLISH;

/**
 * @author Alexey Zhytnik
 * @since 04.06.2016
 */
public class ProductDH {

    private ProductDH() {
    }

    public static Product createProduct() {
        return createProductByType(createTypeWithSingleField());
    }

    public static Product createProductByType(DynamicType type) {
        final Product p = new Product();
        p.setId(85L);
        p.setDynamicType(type);
        p.setDynamicFieldsValues(new Object[type.getFields().size()]);
        p.setCode(88L);
        p.setShortName("product_1");
        p.setTitle(createMultiString("title", 1L));
        p.setKeywords(newHashSet("hot", "best"));
        p.setDescription(createMultiString("description", 2L));
        p.setComments(newHashSet(createComment("comment")));
        return p;
    }

    private static MultilanguageString createMultiString(String text, Long id) {
        final MultilanguageString title = new MultilanguageString();
        title.setId(id);

        final MultilanguageTranslation t = new MultilanguageTranslation();
        t.setTranslation(text);
        t.setLanguage(ENGLISH);
        t.setDefault(true);
        t.setId(id);

        title.add(t);
        return title;
    }

    private static Comment createComment(String text) {
        final Comment comment = new Comment();
        comment.setText(text);
        comment.setId(55L);
        return comment;
    }
}
