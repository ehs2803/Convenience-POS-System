package com.example.convenience_pos_system.controller;

import com.example.convenience_pos_system.domain.LoginForm;
import com.example.convenience_pos_system.domain.Member;
import com.example.convenience_pos_system.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/member")
public class MemberController {
    final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value = "/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form){
        return "member/loginForm";
    }

    @PostMapping(value = "/login")
    public String login(@RequestParam(name = "email") String email, @RequestParam(name = "password") String password,
                        HttpServletRequest request){

        Member loginmember = memberService.login(email, password);
        if(loginmember==null){

            return "/member/loginForm";
        }

        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession(true);
        //세션에 로그인 회원 정보 보관
        session.setAttribute("loginMember", loginmember);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        //세션 삭제.
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 제거
        }
        return "redirect:/";
    }

    @GetMapping(value = "/signup")
    public String signup(){
        return "member/addMemberForm";
    }

    @PostMapping(value = "/signup")
    public String registerMember(HttpServletRequest request){
        String email = request.getParameter("email");
        String pwd = request.getParameter("password");
        String pwdconfirm = request.getParameter("passwordcf");
        String name = request.getParameter("name");
        String role = request.getParameter("role");

        if(!pwd.equals(pwdconfirm)) return "redirect:/member/signup";

        Member member = new Member(email, pwd, name, role);
        memberService.signup(member);

        return "redirect:/member/login";
    }

}
