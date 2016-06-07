package com.zhytnik.shop.util;

import com.zhytnik.shop.App;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.DefaultPropertiesPersister;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * @author Alexey Zhytnik
 * @since 07.06.2016
 */
class PropertyPlaceholder extends PropertyPlaceholderConfigurer {

    private Properties customProperties = new Properties();

    public PropertyPlaceholder() {
    }

    @Override
    protected Properties mergeProperties() throws IOException {
        return merge(super.mergeProperties(), customProperties);
    }

    private Properties merge(Properties goal, Properties additional) throws IOException {
        for (Entry<Object, Object> prop : additional.entrySet()) {
            goal.setProperty((String) prop.getKey(), (String) prop.getValue());
        }
        return goal;
    }

    public void setSupportCustomProperties(boolean withCustom) {
        if (withCustom) customProperties = loadCustomProperties();
    }

    private Properties loadCustomProperties() {
        final FileSystemResource propertyFile = new FileSystemResource(resolveSystemProperty(App.SETTINGS));
        final Properties properties = new Properties();
        try {
            new DefaultPropertiesPersister().load(properties, propertyFile.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    @Override
    public void setLocation(Resource location) {
        super.setLocation(location);
    }
}
