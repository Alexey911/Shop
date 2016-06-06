package com.zhytnik.shop.backend.tool;

import org.hibernate.Cache;
import org.hibernate.SessionFactory;

/**
 * @author Alexey Zhytnik
 * @since 06.06.2016
 */
public class SessionUtil {

    private static SessionUtil instance;

    private SessionFactory sessionFactory;

    private SessionUtil() {
        instance = this;
    }

    public static void resetCache() {
        if (containCache()) {
            final Cache cache = instance.sessionFactory.getCache();
            cache.evictEntityRegions();
            cache.evictCollectionRegions();
            cache.evictDefaultQueryRegion();
            cache.evictQueryRegions();
        }
    }

    private static boolean containCache() {
        return instance != null && instance.sessionFactory != null &&
                instance.sessionFactory.getCache() != null;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
