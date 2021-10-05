package com.springBootApi.api.global.config.security.token.model;

import lombok.*;
import org.hibernate.usertype.UserType;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Token {

    private Long id;

    private String password;

    private UserType userType;

}
