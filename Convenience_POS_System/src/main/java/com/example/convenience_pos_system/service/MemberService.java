package com.example.convenience_pos_system.service;

import com.example.convenience_pos_system.dao.*;
import com.example.convenience_pos_system.domain.Member;
import com.example.convenience_pos_system.domain.ProductHistory;
import com.example.convenience_pos_system.domain.ProductStateHistory;
import com.example.convenience_pos_system.domain.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    final MemberDao memberDao;
    final ProductHistoryDao productHistoryDao;
    final ProductStateHistoryDao productStateHistoryDao;
    final SaleDao saleDao;

    public MemberService(MemberDao memberDao, ProductHistoryDao productHistoryDao,
                         ProductStateHistoryDao productStateHistoryDao, SaleDao saleDao) {
        this.memberDao = memberDao;
        this.productHistoryDao = productHistoryDao;
        this.productStateHistoryDao = productStateHistoryDao;
        this.saleDao = saleDao;
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

    public List<Member> findAll(){
        return memberDao.selectAll();
    }

    public List<ProductHistory> findProductHistoriesByMemberId(Long id){
        return productHistoryDao.selectByMid(id);
    }

    public List<ProductStateHistory> findProductStateHistoriesByMemberId(Long id){
        return productStateHistoryDao.selectByMid(id);
    }

    public Member findbyId(Long id){
        return memberDao.selectById(id);
    }

    public List<Sale> findByMid(Long mid){
        return saleDao.selectByMid(mid);
    }
}
