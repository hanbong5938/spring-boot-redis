package com.springBootApi.api.domain.user.common.factory;


import com.springBootApi.api.domain.user.admin.service.AdminSignService;
import com.springBootApi.api.domain.user.common.model.UserType;
import com.springBootApi.api.domain.user.common.service.SignService;
import com.springBootApi.api.domain.user.member.service.MemberSignService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignFactory {

    private final MemberSignService memberSignService;
    private final AdminSignService adminSignService;

    public SignService getType(final UserType userType) throws IllegalAccessException {

        final SignService signService;
        switch (userType) {
            case M -> signService = memberSignService;
            case A -> signService = adminSignService;
            default -> throw new IllegalAccessException();
        }
        return signService;
    }
}