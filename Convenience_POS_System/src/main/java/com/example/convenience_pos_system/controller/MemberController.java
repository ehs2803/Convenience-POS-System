package com.example.convenience_pos_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/member")
public class MemberController {

    @GetMapping(value = "/login")
    public String login(){
        return "member/loginForm";
    }

    @GetMapping(value = "/signup")
    public String signup(){
        return "member/addMemberForm";
    }

    @PostMapping(value = "/signup")
    public String registerMember(){

        return "redirect:/";
    }

}
