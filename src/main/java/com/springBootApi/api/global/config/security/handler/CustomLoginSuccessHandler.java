package com.springBootApi.api.global.config.security.handler;

import com.checker.self.security.constant.AuthConstants;
import com.checker.self.app.user.model.User;
import com.checker.self.app.user.model.auth.CustomUserDetails;
import com.checker.self.util.token.TokenUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j2
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        log.info("[onAuthenticationSuccess] request : {}, response : {}, authentication : {}", request.toString(), response.toString(), authentication.toString());
        User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();
        String token = TokenUtils.generateJwtToken(user);
        response.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE + " " + token);
    }

}
