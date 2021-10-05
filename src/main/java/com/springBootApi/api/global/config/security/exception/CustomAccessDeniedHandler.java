package com.springBootApi.api.global.config.security.exception;

import com.springBootApi.api.global.error.ErrorResponse;
import com.springBootApi.api.global.error.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * 권한 없는 경우 처리
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        final ErrorCode errorCode = ErrorCode.HANDLE_ACCESS_DENIED;
        response.setStatus(errorCode.getStatus());
        response.setContentType("application/json;charset=utf-8");
        // json 형태로 변환
        String json = ErrorResponse.of(errorCode).convertToJson();
        response.getWriter().write(json);
    }
}