package com.example.convenience_pos_system.controller;

import com.example.convenience_pos_system.domain.Member;
import com.example.convenience_pos_system.domain.Product;
import com.example.convenience_pos_system.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "/product")
public class ProductController {
    final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/add")
    public String addIndex(Model model){
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "product/add";
    }

    @GetMapping(value = "/add/new")
    public String addNewProductForm(){
        return "product/addNewProductForm";
    }

    @PostMapping(value = "/add/new")
    public String addNewProduct(@ModelAttribute Product product, HttpServletRequest request) {
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession(true);
        Member loginMember = (Member) session.getAttribute("loginMember");

        productService.addNewProduct(product, loginMember.getId());

        return "redirect:/product/add";
    }
}