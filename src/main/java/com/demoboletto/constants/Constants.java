package com.demoboletto.constants;

import java.util.List;

public class Constants {
    public static String CLAIM_USER_ID = "uuid";
    public static String CLAIM_USER_ROLE = "role";
    public static String ACCESS_COOKIE_NAME = "access_token";
    public static String REFRESH_COOKIE_NAME = "refresh_token";
    public static String BEARER_PREFIX = "Bearer ";
    public static String AUTHCODE_PREFIX = "AuthCD ";
    public static String AUTHORIZATION_HEADER = "Authorization";
    public static String DEFAULT_IMAGE = "https://areyousleeping.s3.ap-northeast-2.amazonaws.com/KakaoTalk_Photo_2024-03-24-05-26-45.png";

    public static List<String> NO_NEED_AUTH_URLS = List.of(
            "/api/v1/no-auth/**", "/api/v1/oauth/login/**",
            "/api/v1/auth/reissue",
            "/api/v1/oauth2/login/apple",
            "/api/v1/oauth2/callback/apple",
            "/api/v1/auth/sign-up",
            "/api/v1/auth/email-duplicate",
            "/api-docs.html",
            "/api-docs/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/app/**",
            "/invite/**",
            "/images/**",
            "/subscribe/**");

    public static List<String> USER_URLS = List.of(
            "/api/v1/users/**");
}