package com.example.blood_pressure_diary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping(path = "/login")
    public String loadLoginPage() {
        return "login";
    }

    @GetMapping(path = "/index.html")
    public String loadIndexPage() {
        return "index";
    }
}
