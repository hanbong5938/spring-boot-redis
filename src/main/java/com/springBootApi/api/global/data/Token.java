package com.springBootApi.api.global.data;

public class Token {
    // 토큰 이름
    public static final String AUTH = "X-AUTH-TOKEN";

    // 리프레시 토큰 명
    public static final String REFRESH_TOKEN = "refresh_token";

    // 만료 시간
    public static final int REFRESH_TOKEN_TIME = 60 * 60; // 1 hour

}
