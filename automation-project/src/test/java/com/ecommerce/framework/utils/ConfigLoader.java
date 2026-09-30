package com.ecommerce.framework.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigLoader {
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream inputStream = ConfigLoader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (inputStream == null) {
                throw new IllegalStateException("config.properties not found in classpath");
            }
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load config.properties", e);
        }
    }

    private ConfigLoader() {
    }

    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }

    public static String getBaseUrl() {
        return getProperty("baseUrl");
    }

    public static String getApiBaseUrl() {
        return getProperty("apiBaseUrl");
    }
}
