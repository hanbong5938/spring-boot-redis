package com.springBootApi.api.global.config.security.interceptor;

import com.checker.self.security.constant.AuthConstants;
import com.checker.self.util.token.TokenUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class JwtTokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        log.info("[preHandle] request : {}, response : {}, handler : {}", request.toString(), response.toString(), handler.toString());
        String header = request.getHeader(AuthConstants.AUTH_HEADER);

        if (header != null) {
            String token = TokenUtils.getTokenFromHeader(header);
            if (TokenUtils.isValidToken(token)) {
                return true;
            }
        }
        response.sendRedirect("/error/unauthorized");
        return false;

    }

}
