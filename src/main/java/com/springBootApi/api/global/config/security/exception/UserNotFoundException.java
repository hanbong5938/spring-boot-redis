package com.springBootApi.api.global.config.security.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String phone){
        super(phone + " NotFoundException");
    }

}
