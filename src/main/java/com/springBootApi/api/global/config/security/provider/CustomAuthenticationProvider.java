package com.springBootApi.api.global.config.security.provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

@Log4j2
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private UserDetailsService userDetailsService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("[authenticate] authentication : {}", authentication.toString());
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String phone = token.getName();
        String password = authentication.getCredentials().toString();
        CustomUserDetails customUserDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(phone);
        if (!bCryptPasswordEncoder.matches(password, customUserDetails.getPassword())) {
            throw new BadCredentialsException(customUserDetails.getUsername() + "Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(customUserDetails, password, customUserDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        log.info("[supports] authentication : {}", authentication.toString());
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
