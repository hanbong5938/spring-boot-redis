package com.springBootApi.api.domain.user.common.exception;

import com.springBootApi.api.global.error.exception.BusinessException;
import com.springBootApi.api.global.error.exception.ErrorCode;

public class SignInFailException extends BusinessException {

    public SignInFailException() {
        super(ErrorCode.LOGIN_INPUT_INVALID);
    }
}
