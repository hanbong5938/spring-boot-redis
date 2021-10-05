package com.springBootApi.api.domain.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Email {

    /**
     * 이메일
     * */
    @javax.validation.constraints.Email(message = "{email_regexp}")
    @Column(length = 50, name = "email")
    @NotEmpty
    private String value;

    /**
     * 이메일 생성 빌더
     * */
    public static Email of(String value) {
        return Email.builder().value(value).build();
    }

    /**
     * 이메일 도메인 추출
     * */
    public String getHost() {
        final int index = value.indexOf("@");
        return index == -1 ? null : value.substring(index + 1);
    }

    /**
     * 이메일 @ 앞 추출
     * */
    public String getId() {
        final int index = value.indexOf("@");
        return index == -1 ? null : value.substring(0, index);
    }
}