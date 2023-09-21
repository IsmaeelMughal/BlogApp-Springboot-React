package com.testproject.blogapp.utils;

import java.util.Arrays;
import java.util.List;

public class Constants {
    public static final String AUTHORIZATION = "Authorization";
    public static final String SCHEMA_NAME = "blog_app";
    public static final String BEARER = "Bearer ";
    public static final List<String> CORS_ALLOWED_ORIGINS = List.of("http://127.0.0.1:3000", "http://localhost:3000");
    public static final List<String> CORS_ALLOWED_HEADERS = Arrays.asList(
            "Origin", "Access-Control-Allow-Origin", "Content-Type",
            "Accept", "Authorization", "Origin, Accept", "X-Requested-With",
            "Access-Control-Request-Method", "Access-Control-Request-Headers");
    public static final List<String> CORS_EXPOSED_HEADERS =Arrays.asList(
            "Origin", "Content-Type", "Accept", "Authorization",
            "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
    public static final List<String> CORS_ALLOWED_METHODS =Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH");
    public static final String CORS_CONFIGURATION_PATTERN = "/**";
    public static final String AUTH_FILTER_PATH = "/api/auth/**";
    public static final String POST_FILTER_PATH = "/post/**";
    public static final String ADMIN_FILTER_PATH = "/admin/**";
    public static final String USER_FILTER_PATH = "/user/**";
    public static final String MODERATOR_FILTER_PATH = "/moderator/**";
    public static final String ADMIN_FILTER_ROLE = "ADMIN";
    public static final String MODERATOR_FILTER_ROLE = "MODERATOR";
    public static final String USER_FILTER_ROLE = "USER";

}
