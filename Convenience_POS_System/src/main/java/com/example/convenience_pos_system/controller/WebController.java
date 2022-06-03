package com.example.convenience_pos_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebController {

    @GetMapping(value = "/")
    public String main(){
        return "mainpage";
    }

}
