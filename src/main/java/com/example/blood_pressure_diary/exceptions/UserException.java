package com.example.blood_pressure_diary.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserException extends RuntimeException{

    public static final String USER_NAME_EXIST = "User name exist, please change user name";
    public static final String USER_EMAIL_EXIST = "Email exist, please change email";
    public static  final String USER_NAME_CANT_BE_NULL = "User name can't be null";
    public static  final String USER_EMAIL_CANT_BE_NULL = "User email can't be null";
    public static final String PASSWORD_CANT_BE_NULL = "Password can't be null";

    public UserException(String message) {
        super(message);
    }
}
