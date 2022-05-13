package com.example.convenience_pos_system.config;

import com.example.convenience_pos_system.controller.MemberController;
import com.example.convenience_pos_system.dao.MemberDao;
import com.example.convenience_pos_system.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


@Configuration
public class JavaConfig {

    @Bean
    public MemberController memberController(){
        return new MemberController(memberService());
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberDao());
    }

    @Bean
    public MemberDao memberDao(){
        return new MemberDao(dataSource());
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource datasource = new DriverManagerDataSource();
        datasource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        datasource.setUrl("jdbc:mysql://127.0.0.1:3306/pos");
        datasource.setUsername("root");
        datasource.setPassword("1234");
        return datasource;
    }
}
