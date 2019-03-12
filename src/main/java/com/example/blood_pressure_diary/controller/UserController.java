package com.example.blood_pressure_diary.controller;

import com.example.blood_pressure_diary.model.User;
import com.example.blood_pressure_diary.service.UserService;
import com.example.blood_pressure_diary.validator.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    private final UserService userService;
    private final UserValidator userValidator;

    public UserController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping(value = {"/addUser"})
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "addUser";
    }

    @PostMapping(path = "/addUser")
    public String createUser(User user) {
        if(!userService.validateUserName(user).isEmpty()){
            return "redirect:/errorNamePage";
        }
        if(!userService.validateUserEmail(user).isEmpty()){
            return "redirect:/errorEmailPage";
        }
        userService.create(user);
        return "redirect:/index";
    }


    @RequestMapping(value = {"/errorNamePage"}, method = RequestMethod.GET)
    public String errorNamePage(Model model) {
        return "errorNamePage";
    }


    @RequestMapping(value = {"/errorEmailPage"}, method = RequestMethod.GET)
    public String errorEmailPage(Model model) {
        return "errorEmailPage";
    }
}
