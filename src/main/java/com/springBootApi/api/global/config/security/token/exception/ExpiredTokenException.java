package com.springBootApi.api.global.config.security.token.exception;

import com.springBootApi.api.global.error.exception.BusinessException;
import com.springBootApi.api.global.error.exception.ErrorCode;

public class ExpiredTokenException extends BusinessException {

    public ExpiredTokenException() {
        super(ErrorCode.EXPIRED_TOKEN);
    }
}