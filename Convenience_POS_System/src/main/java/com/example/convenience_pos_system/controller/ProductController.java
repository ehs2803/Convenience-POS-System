package com.example.convenience_pos_system.controller;

import com.example.convenience_pos_system.domain.Member;
import com.example.convenience_pos_system.domain.Product;
import com.example.convenience_pos_system.domain.ProductHistory;
import com.example.convenience_pos_system.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping(value = "/add/{productId}")
    public String addProductQuantityForm(@PathVariable Long productId, Model model){
        Product product = productService.findById(productId);
        model.addAttribute("product", product);
        return "product/addProductQuantity";
    }

    @PostMapping(value = "/add/{productId}")
    public String addProductQuantity(HttpServletRequest request){
        int addQuantity = Integer.parseInt(request.getParameter("addQuantity"));
        Long id = Long.valueOf(request.getParameter("id"));

        Product product = productService.findById(id);
        int addedQuantity = product.getQuantity()+addQuantity;
        product.setQuantity(addedQuantity);

        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession(true);
        Member loginMember = (Member) session.getAttribute("loginMember");

        productService.addQuantity(product, addQuantity, loginMember.getId());

        return "redirect:/product/add";
    }

    @GetMapping(value = "/list")
    public String productList(Model model){
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);

        return "product/productList";
    }

    @GetMapping(value = "/list/{productId}")
    public String productHistoryList(@PathVariable Long productId, Model model){
        List<ProductHistory> productHistories = productService.findProductHistoryById(productId);
        model.addAttribute("productHistories", productHistories);

        return "product/productHistoryList";
    }

    @GetMapping(value = "/update")
    public String updateList(Model model){
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);

        return "product/updateList";
    }

    @PostMapping(value = "/update/")
    public String updateProduct(@ModelAttribute Product product){
        productService.UpdateProduct(product);
        return "redirect:/product/update";
    }

    @GetMapping(value = "/update/{productId}")
    public String updateProductForm(@PathVariable Long productId, Model model){
        Product product = productService.findById(productId);
        model.addAttribute("product",product);

        return "product/updateProductForm";
    }
}