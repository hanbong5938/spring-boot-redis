package com.springBootApi.api.domain.user.common.factory;

import com.springBootApi.api.domain.user.admin.service.AdminUserDetailsService;
import com.springBootApi.api.domain.user.common.model.UserType;
import com.springBootApi.api.domain.user.member.service.MemberUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailsFactory {

    private final AdminUserDetailsService adminUserDetailsService;
    private final MemberUserDetailsService memberUserDetailService;

    public UserDetailsService getType(final UserType userType) throws IllegalAccessException {

        final UserDetailsService userDetailsService;
        switch (userType) {
            case M -> userDetailsService = memberUserDetailService;
            case A -> userDetailsService = adminUserDetailsService;
            default -> throw new IllegalAccessException();
        }
        return userDetailsService;
    }
}