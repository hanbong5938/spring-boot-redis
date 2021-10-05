package com.springBootApi.api.infra.sms.exception;

import com.springBootApi.api.global.error.exception.BusinessException;
import com.springBootApi.api.global.error.exception.ErrorCode;

public class SmsSendFailException extends BusinessException {

    public SmsSendFailException() {
        super(ErrorCode.SMS_SEND_FAIL);
    }
}