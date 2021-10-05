package com.springBootApi.api.domain.user.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserType {
    A("Admin"),
    M("Member");

    private String value;
}
