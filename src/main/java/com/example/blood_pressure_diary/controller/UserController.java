package com.example.blood_pressure_diary.controller;

import com.example.blood_pressure_diary.model.User;
import com.example.blood_pressure_diary.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import static org.springframework.cglib.beans.BeanMap.create;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = {"/addUser"})
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "addUser";
    }

    @PostMapping(path = "/addUser")
    public String createUser(User user) {
        userService.create(user);
        return "redirect:/index";
    }
}
