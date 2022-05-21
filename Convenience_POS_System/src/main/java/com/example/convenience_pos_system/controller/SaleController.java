package com.example.convenience_pos_system.controller;

import com.example.convenience_pos_system.domain.Member;
import com.example.convenience_pos_system.domain.Product;
import com.example.convenience_pos_system.domain.SaleCart;
import com.example.convenience_pos_system.domain.SaleCartDetail;
import com.example.convenience_pos_system.service.ProductService;
import com.example.convenience_pos_system.service.SaleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/sale")
public class SaleController {
    final SaleService saleService;
    final ProductService productService;

    public SaleController(SaleService saleService, ProductService productService) {
        this.saleService = saleService;
        this.productService = productService;
    }

    @GetMapping(value = "")
    public String sale(HttpServletRequest request, Model model){
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession(true);
        Member loginMember = (Member) session.getAttribute("loginMember");

        List<Product> productList = productService.findAll();
        model.addAttribute("productList", productList);

        List<SaleCart> saleCartList = saleService.getAllCartByMid(loginMember.getId());
        model.addAttribute("saleCartList",saleCartList);

        List<SaleCartDetail> saleCartDetailList = new ArrayList<>();

        int totalCost = 0;
        if(saleCartList!=null){
            for(int i=0;i<saleCartList.size();i++){
                Product product = productService.findById(saleCartList.get(i).getPid());
                totalCost += (product.getPrice()*saleCartList.get(i).getQuantity());
                SaleCartDetail saleCartDetail = new SaleCartDetail(saleCartList.get(i).getMid(),
                        saleCartList.get(i).getPid(), product.getName(), product.getPrice(),
                        saleCartList.get(i).getQuantity());
                saleCartDetailList.add(saleCartDetail);
            }
        }
        model.addAttribute("totalCost", totalCost);
        model.addAttribute("saleCartDetailList", saleCartDetailList);

        return "sale/sale";
    }

    @PostMapping(value = "/add")
    public String add(HttpServletRequest request){
        Long pid = Long.valueOf(request.getParameter("pid"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession(true);
        Member loginMember = (Member) session.getAttribute("loginMember");

        saleService.addCart(loginMember.getId(), pid, quantity);

        return "redirect:/sale";
    }

    @PostMapping("/delete/{pid}")
    public String delete(HttpServletRequest request, @PathVariable("pid") Long pid){
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession(true);
        Member loginMember = (Member) session.getAttribute("loginMember");

        saleService.deleteSaleCart(loginMember.getId(), pid);

        return "redirect:/sale";
    }

    @GetMapping("/reset")
    public String reset(HttpServletRequest request){
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession(true);
        Member loginMember = (Member) session.getAttribute("loginMember");

        saleService.deleteAllSaleCart(loginMember.getId());

        return "redirect:/sale";
    }

    @GetMapping("/calculate")
    public String calculate(HttpServletRequest request){
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession(true);
        Member loginMember = (Member) session.getAttribute("loginMember");

        List<SaleCart> saleCartList = saleService.getAllCartByMid(loginMember.getId());

        return "redirect:/sale";
    }
}
