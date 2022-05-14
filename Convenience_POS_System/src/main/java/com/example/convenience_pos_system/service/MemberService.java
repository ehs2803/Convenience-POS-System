package com.example.convenience_pos_system.service;

import com.example.convenience_pos_system.dao.MemberDao;
import com.example.convenience_pos_system.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;

public class MemberService {
    final MemberDao memberDao;

    @Autowired
    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public void signup(Member member){
        memberDao.insert(member);
    }

    public Member login(String email, String password){
        Member member =  memberDao.selectByEmail(email);
        if(member==null) return null;
        if(member.getPassword().equals(password)){
            return member;
        }
        else return null;
    }

    public Member update(String email, String name, String pwd, String role){
        Member updatemember = memberDao.selectByEmail(email);
        updatemember.setName(name);
        updatemember.setPassword(pwd);
        updatemember.setRole(role);
        memberDao.update(updatemember);
        return updatemember;
    }
}
