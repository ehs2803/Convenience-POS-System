package com.example.convenience_pos_system;

import com.example.convenience_pos_system.config.JavaConfig;
import com.example.convenience_pos_system.dao.MemberDao;
import com.example.convenience_pos_system.domain.Member;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestMain {
    public static void main(String [] args){
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(JavaConfig.class);
        MemberDao memSvc = (MemberDao)ctx.getBean("memberDao");
        Member member = new Member("test2@naver.com","1234","test2","MANAGER");
        memSvc.insert(member);
        System.out.println(member.getId());
        ctx.close();
    }
}
