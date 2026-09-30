package com.ecommerce.framework.api.api;

import com.ecommerce.framework.utils.ConfigLoader;

public final class ApiConstants {
    public static final String BASE_URL = ConfigLoader.getApiBaseUrl();
    public static final String POSTS_ENDPOINT = "/posts";
    public static final String COMMENTS_ENDPOINT = "/comments";

    private ApiConstants() {
    }
}
