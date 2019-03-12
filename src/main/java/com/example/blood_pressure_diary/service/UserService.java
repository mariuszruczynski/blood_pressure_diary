package com.example.blood_pressure_diary.service;

import com.example.blood_pressure_diary.entity.UserEntity;
import com.example.blood_pressure_diary.exceptions.UserException;
import com.example.blood_pressure_diary.model.User;
import com.example.blood_pressure_diary.repository.UserRepository;
import com.example.blood_pressure_diary.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.isNull;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserValidator userValidator;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       UserValidator userValidator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userValidator = userValidator;
    }

    public void create(User user) {
        userValidator.validateUser(user);
        UserEntity userEntity = new UserEntity();
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(userEntity);
    }

    public List<String> validateUserEmail(User user) {
        List<String> errors = new ArrayList<>();
        if (!isNull(userRepository.findByUserEmail(user.getEmail()))) {
            errors.add(UserException.USER_EMAIL_EXIST);
        }
        if (!isNull(userRepository.findByUsername(user.getName()))) {
            errors.add(UserException.USER_NAME_EXIST);
        }
        return errors;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(userName);
        return toUserDetails(userEntity);
    }

    private UserDetails toUserDetails(UserEntity userEntity) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(userEntity.getName())
                .password(userEntity.getPassword())
                .authorities(Collections.EMPTY_LIST)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .build();
    }

    public UserEntity findUserByName(String name) {
        return userRepository.findByUsername(name);
    }

    public List<String> validateUserName(User user) {
        List<String> errors = new ArrayList<>();
        if (!isNull(userRepository.findByUsername(user.getName()))) {
            errors.add(UserException.USER_NAME_EXIST);
        }
        return errors;

    }
}
