package com.springBootApi.api.global.config.security.exception;

import com.springBootApi.api.global.error.ErrorResponse;
import com.springBootApi.api.global.error.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        String exception = (String) request.getAttribute("exception");


        log.debug("log: exception: {} ", exception);

        //토큰 없는 경우
        if (exception == null) {
            setResponse(response, ErrorCode.INVALID_TOKEN);
        } else {
            setResponse(response, ErrorCode.of(exception));
        }
    }

    /**
     * 한글 출력을 위해 getWriter() 사용
     */
    private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(errorCode.getStatus());
        response.setContentType("application/json;charset=utf-8");
        // json 형태로 변환
        String json = ErrorResponse.of(errorCode).convertToJson();
        response.getWriter().write(json);
    }

}