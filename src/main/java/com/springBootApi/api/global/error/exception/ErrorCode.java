package com.springBootApi.api.global.error.exception;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
    // Common
    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value"),
    ENTITY_NOT_FOUND(400, "C003", " Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
    INVALID_TYPE_VALUE(400, "C005", " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),
    SMS_SEND_FAIL(500, "C007", "SMS send Failed"),
    EXPIRED_TOKEN(401, "C008", "Token is Expired"),
    INVALID_TOKEN(401, "C009", "Token is invalid"),
    LOGIN_INPUT_INVALID(401, "C010", "Token is invalid");



//    // Member
//    EMAIL_DUPLICATION(400, "M001", "Email is Duplication"),
//    LOGIN_INPUT_INVALID(400, "M002", "Login input is invalid"),
//
//    // Coupon
//    COUPON_ALREADY_USE(400, "CO001", "Coupon was already used"),
//    COUPON_EXPIRE(400, "CO002", "Coupon was already expired");

    private final String code;
    private final String message;
    private final int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }

    /**
     * 코드 생성
     * */
    public static ErrorCode of(final String code) {
        return ErrorCode.valueOf(CODE_MAP.get(code));
    }

    /**
     * 초기 실행시 한번만 실행
     * code name 형태로 맵 생성
     */
    private static final Map<String, String> CODE_MAP = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(ErrorCode::getCode, ErrorCode::name)));
}
