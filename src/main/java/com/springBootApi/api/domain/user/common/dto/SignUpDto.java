package com.springBootApi.api.domain.user.common.dto;

import com.springBootApi.api.domain.model.Email;
import com.springBootApi.api.domain.model.Phone;
import com.springBootApi.api.domain.user.admin.model.Admin;
import com.springBootApi.api.domain.user.common.model.UserType;
import com.springBootApi.api.domain.user.member.model.Member;
import com.springBootApi.api.global.data.Regexp;
import com.springBootApi.api.global.util.message.Message;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

public class SignUpDto {
    @ApiModel(value = "회원 가입 정보",
            description = "하나로 Member, Employer 두가지 동시에 사용하기에 사업자 등록번호, 기업체명은 필수 값 제외.")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Req {

        @ApiModelProperty(value = "핸드폰 번호", required = true)
        @Pattern(regexp = Regexp.PHONE, message = "{phone_regexp}")
        private String phone;

        @ApiModelProperty(value = "비밀번호", required = true)
        @NotEmpty(message = "{please_insert_password}")
        private String password;

        @ApiModelProperty(value = "회원 타입", required = true)
        private UserType userType;

        @ApiModelProperty(value = "회원 이름", required = true)
        @NotEmpty(message = "{please_insert_name}")
        private String name;

        @ApiModelProperty(value = "이메일", required = true)
        @javax.validation.constraints.Email(message = "{email_regexp}")
        private String email;

        /**
         * signUpRequest -> Member 변환
         */
        public Member toMember() {
            return Member.builder()
                    .phone(Phone.of(this.phone))
                    .password(this.password)
                    .name(this.name)
                    .email(Email.of(this.email))
                    .build();
        }

        /**
         * signUpRequest -> Member 변환
         */
        public Admin toAdmin() {
            return Admin.builder()
                    .phone(Phone.of(this.phone))
                    .password(this.password)
                    .name(this.name)
                    .email(Email.of(this.email))
                    .build();
        }
    }

    @ApiModel(value = "회원 가입 리턴 정보",
            description = "리턴 정보 현재 없으나 유지 보수 위해 생성")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Res {

        @ApiModelProperty(value = "가입 메세지")
        private String message;

        /**
         * 가입 이후 메세지 빌더
         * */
        public static Res of() {
            return Res.builder().message(Message.getMessage("sign_up_response.message")).build();
        }

    }
}