package com.springBootApi.api.domain.user.common.service;

import com.springBootApi.api.domain.user.common.dto.SignInDto;
import com.springBootApi.api.domain.user.common.dto.SignUpDto;
import com.springBootApi.api.domain.user.common.dto.TokenDto;

public interface SignService {
    SignUpDto.Res signUp(SignUpDto.Req req);

    TokenDto.Res signIn(SignInDto.Req req);
}
