package com.example.convenience_pos_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping(value = "/")
    public String main(){
        return "mainpage";
    }

    @GetMapping(value = "/about")
    public String about(){
        return "about";
    }

    @GetMapping(value = "/contact")
    public String contact(){
        return "contact";
    }

}
