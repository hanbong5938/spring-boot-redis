package com.springBootApi.api.global.config.security.token.filter;

import com.springBootApi.api.global.config.security.token.exception.ExpiredTokenException;
import com.springBootApi.api.global.config.security.token.provider.JwtTokenProvider;
import com.springBootApi.api.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.Authentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Request 로 들어오는 Jwt Token 의 유효성을 검증 (jwtTokenProvider.validateToken) 하는
     * filter 를 filterChain 에  등록
     *
     * @param request     jwtToken 추출
     * @param response    filterChain 에 전송할 response
     * @param filterChain 토큰 유효성 검증하기 위한 filter 를 filter chain 에 등록
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String token = jwtTokenProvider.resolveToken(request);

        try {

            if (Objects.nonNull(token) && jwtTokenProvider.isValidateToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

            // EntryPoint 에서 에러 처리 해주기 위해 설정
        } catch (ExpiredTokenException e) {
            request.setAttribute("exception", ErrorCode.EXPIRED_TOKEN.getCode());
            e.printStackTrace();
        } catch (Exception e) {
            request.setAttribute("exception", ErrorCode.INVALID_TOKEN.getCode());
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }

}
