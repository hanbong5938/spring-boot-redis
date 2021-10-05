package com.springBootApi.api.domain.user.common.dto;

import com.springBootApi.api.domain.user.common.model.UserType;
import com.springBootApi.api.global.data.Regexp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

public class SignInDto {
    @ApiModel(value = "로그인 정보", description = "핸드폰 번호, 패스워드, 회원 타입")
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class Req {

        @ApiModelProperty(value = "핸드폰 번호", required = true)
        @Pattern(regexp = Regexp.PHONE, message = "{phone_regexp}")
        private String id;

        @ApiModelProperty(value = "비밀번호", required = true)
        private String password;

        @ApiModelProperty(value = "회원 타입", required = true)
        private UserType userType;

    }
}