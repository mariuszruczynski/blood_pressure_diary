package com.example.blood_pressure_diary.validator;

import com.example.blood_pressure_diary.exceptions.UserException;
import com.example.blood_pressure_diary.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    public void validateUser(User user) {
        if (user.getName().equals("")) {
            throw new UserException(UserException.USER_NAME_CANT_BE_NULL);
        }
        if (user.getEmail().equals("")) {
            throw new UserException(UserException.USER_EMAIL_CANT_BE_NULL);
        }
        if (user.getPassword().equals("")) {
            throw new UserException(UserException.PASSWORD_CANT_BE_NULL);
        }
    }
}
