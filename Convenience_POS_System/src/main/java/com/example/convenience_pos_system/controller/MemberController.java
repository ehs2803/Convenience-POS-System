package com.example.convenience_pos_system.controller;

import com.example.convenience_pos_system.domain.*;
import com.example.convenience_pos_system.domain.MemberUpdateDto;
import com.example.convenience_pos_system.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/member")
public class MemberController {
    final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value = "/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form, HttpServletRequest request, Model model){
        String redirectUrl = request.getParameter("redirectURL");
        model.addAttribute("url" ,redirectUrl);
        System.out.println(redirectUrl);
        return "member/loginForm";
    }

    @PostMapping(value = "/login")
    public String login(@RequestParam(name = "email") String email, @RequestParam(name = "password") String password,
                        @RequestParam(name = "redirectURL",defaultValue = "/") String redirectURL,
                        HttpServletRequest request, Model model){
        Map<String, String> errors = new HashMap<>();
        Member loginmember = memberService.login(email, password);
        if(loginmember==null){
            errors.put("loginError", "아이디, 비밀번호를 확인하세요.");
            model.addAttribute("errors",errors);
            return "/member/loginForm";
        }

        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession(true);
        //세션에 로그인 회원 정보 보관
        session.setAttribute("loginMember", loginmember);

        return "redirect:"+redirectURL;
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
    public String registerMember(HttpServletRequest request, Model model){
        Map<String, String> errors = new HashMap<>();

        String email = request.getParameter("email");
        String pwd = request.getParameter("password");
        String pwdconfirm = request.getParameter("passwordcf");
        String name = request.getParameter("name");
        String role = request.getParameter("role");

        Member checkMember = memberService.findbyEmail(email);

        if(!StringUtils.hasText(email)){
            errors.put("email", "아이디 이메일은 필수입니다.");
        }
        else if(checkMember!=null){
            errors.put("email","이미 존재하는 아이디 이메일입니다.");
        }
        if(!StringUtils.hasText(pwd)){
            errors.put("password", "비밀번호는 필수입니다.");
        }
        if(!pwd.equals(pwdconfirm)){
            errors.put("passwordConfirm","비밀번호가 일치하지 않습니다.");
        }
        if(!StringUtils.hasText(name)){
            errors.put("name", "이름은 필수입니다.");
        }

        if(!errors.isEmpty()){
            model.addAttribute("errors",errors);
            return "member/addMemberForm";
        }
        //if(!pwd.equals(pwdconfirm)) return "redirect:/member/signup";

        Member member = new Member(email, pwd, name, role);
        memberService.signup(member);

        return "redirect:/member/login";
    }

    @GetMapping(value = "/detail")
    public String detail(HttpServletRequest request, Model model){
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession(true);
        Member loginmember = (Member) session.getAttribute("loginMember");

        List<ProductHistory> productHistories = memberService.findProductHistoriesByMemberId(loginmember.getId());
        List<ProductStateHistory> productStateHistories = memberService.findProductStateHistoriesByMemberId(loginmember.getId());
        List<Sale> sales = memberService.findByMid(loginmember.getId());

        model.addAttribute("loginmember",loginmember);
        model.addAttribute("productHistories", productHistories);
        model.addAttribute("productStateHistories", productStateHistories);
        model.addAttribute("sales",sales);

        if(loginmember.getRole().equals("MANAGER")){
            List<Member> members= memberService.findAll();
            model.addAttribute("members", members);
        }

        return "/member/memberDetail";
    }

    @GetMapping(value = "/detail/{memberId}")
    public String detailMemberId(@PathVariable Long memberId, Model model){
        Member member = memberService.findbyId(memberId);
        List<ProductHistory> productHistories = memberService.findProductHistoriesByMemberId(memberId);
        List<ProductStateHistory> productStateHistories = memberService.findProductStateHistoriesByMemberId(memberId);
        List<Sale> sales = memberService.findByMid(memberId);

        model.addAttribute("member",member);
        model.addAttribute("productHistories", productHistories);
        model.addAttribute("productStateHistories", productStateHistories);
        model.addAttribute("sales",sales);

        return "member/memberDetailOther";
    }

    @GetMapping(value = "/update")
    public String update(HttpServletRequest request, Model model){
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession(true);
        Member loginmember = (Member) session.getAttribute("loginMember");

        model.addAttribute("loginmember",loginmember);

        return "/member/updateForm";
    }

    @PostMapping(value = "/update")
    public String updateMember(@ModelAttribute MemberUpdateDto member, HttpServletRequest request, Model model){
        Map<String, String> errors = new HashMap<>();

        if(!StringUtils.hasText(member.getName())){
            errors.put("name", "이름은 필수 항목 입니다.");
        }
        if(!StringUtils.hasText(member.getPassword())){
            errors.put("password", "비밀번호는 필수값 입니다.");
        }

        if(!errors.isEmpty()){
            HttpSession session = request.getSession(true);
            Member loginmember = (Member) session.getAttribute("loginMember");
            model.addAttribute("loginmember",loginmember);

            model.addAttribute("errors",errors);

            return "/member/updateForm";
        }

        Member updatedMember = memberService.update(member.getEmail(), member.getName(),
                member.getPassword(), member.getRole());

        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession(true);
        session.setAttribute("loginMember", updatedMember);

        return "redirect:/member/detail";
    }
}
