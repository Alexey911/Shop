package com.zhytnik.shop.backend.dao;

import com.zhytnik.shop.domain.market.product.Product;
import org.hibernate.internal.util.StringHelper;

import javax.persistence.Query;
import java.util.List;

/**
 * @author Alexey Zhytnik
 * @since 02.06.2016
 */
public class ProductDao extends DynamicEntityDao<Product> {

    public ProductDao() {
        setClass(Product.class);
    }

    public List<Product> findByKeywords(String... keywords) {
        String where = createWhereStatement(keywords);
        final Query query = entityManager.createNativeQuery("SELECT * FROM T_PRODUCT " +
                "WHERE ID IN " +
                "(SELECT PRODUCT_ID FROM KEYWORDS " +
                "WHERE KEYWORDS.KEYWORD IN (" + where + "))", Product.class);
        setKeywords(query, keywords);
        return findByQuery(query);
    }

    private String createWhereStatement(String[] keywords) {
        return StringHelper.repeat("?, ", keywords.length - 1) + "?";
    }

    private void setKeywords(Query query, String[] keywords) {
        for (int i = 0; i < keywords.length; i++) {
            query.setParameter(i + 1, keywords[i]);
        }
    }
}
