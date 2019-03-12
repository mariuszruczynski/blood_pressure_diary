package com.example.blood_pressure_diary.controller;

import com.example.blood_pressure_diary.service.UserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class MainController {

    private final UserUtils userUtils;

    public MainController(UserUtils userUtils) {
        this.userUtils = userUtils;
    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("loggedUser", userUtils.getLoggedUserName());
        return "index";
    }

    @RequestMapping(value = {"/setDateRange"}, method = RequestMethod.GET)
    public String setDateRange(Model model) {
        return "setDateRange";
    }




}
