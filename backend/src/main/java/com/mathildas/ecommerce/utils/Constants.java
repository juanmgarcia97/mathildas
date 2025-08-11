package com.mathildas.ecommerce.utils;

public class Constants {

    public static final String ALLOWED_HTTP_METHODS = "GET, POST, PUT, DELETE, OPTIONS";
    public static final String OPTION_HEADER = "OPTIONS";
    public static final String HTTP_HEADER_ORIGIN = "Access-Control-Allow-Origin";
    public static final String HTTP_HEADER_METHODS = "Access-Control-Allow-Methods";
    public static final String HTTP_HEADER_ALLOW_HEADERS = "Access-Control-Allow-Headers";
    public static final String HTTP_HEADER_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
    public static final String HTTP_HEADER_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
    public static final String HTTP_HEADER_MAX_AGE= "Access-Control-Max-Age";
    public static final String HTTP_HEADER_AUTHORIZATION= "Authorization";
    public static final String HTTP_AUTH_HEADERS= "Authorization, X-PINGOTHER, Content-Type, Accept, X-Requested-With, remember-me, Origin, X-Custom-Header";
    public static final String MAX_AGE= "3600";

    public static final String GRANTED_ROLE= "ROLE_";

    public static final String BEARER = "Bearer ";
    public static final int INDEX_TOKEN_EXTRACTION = 7;

    public static final String AUTH_PATH = "/api/v1/auth/**";
    public static final String ORDER_PATH = "/api/v1/order/**";
    public static final String ADMIN_PATH = "/api/v1/admin/**";
    public static final String CUSTOMER_PATH = "/api/v1/customer/**";

    public static final String REFRESH_TOKEN_PATH = "/api/v1/auth/refresh-token";

    public static final int MIN_PRODUCT_QUANTITY = 1;

    public static final String CONTENT_TYPE = "application/json";

    public static final String TOKEN_EXPIRED_MESSAGE = "Token expired";
    public static final String BAD_CREDENTIALS_MESSAGE = "Your user role is not authorized to perform this action";
    public static final String UNAUTHORIZED_MESSAGE = "Unauthorized";

    public static final String HTTP_ACCESS_COOKIE = "jwt";
    public static final String HTTP_REFRESH_COOKIE = "jwt-refresh";

    public static final Long JWT_EXPIRATION_TIME = 1000L * 30L * 60L; // 1 hour
    public static final Long JWT_REFRESH_EXPIRATION_TIME = 1000L * 60L * 60L * 24L * 7L; // 7 days
}
