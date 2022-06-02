package com.example.convenience_pos_system.controller;

import com.example.convenience_pos_system.domain.*;
import com.example.convenience_pos_system.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String addNewProductForm(Model model){
        model.addAttribute("product", new Product());
        return "product/addNewProductForm";
    }

    @PostMapping(value = "/add/new")
    public String addNewProduct(@Validated @ModelAttribute("product") ProductAddDto product, BindingResult bindingResult,
                                HttpServletRequest request) {

        Product checkProduct = productService.findByCode(product.getCode());
        if(checkProduct!=null){
            bindingResult.addError(new FieldError("product","code","이미 존재하는 코드입니다."));
        }

        if (bindingResult.hasErrors()) {
            return "product/addNewProductForm";
        }

        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession(true);
        Member loginMember = (Member) session.getAttribute("loginMember");

        Product newProduct = new Product(product.getCode(), product.getName(), product.getPrice(), product.getQuantity(),1);
        productService.addNewProduct(newProduct, loginMember.getId());

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

        Product addproduct = productService.findById(id);
        int addedQuantity = addproduct.getQuantity()+addQuantity;
        addproduct.setQuantity(addedQuantity);

        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession(true);
        Member loginMember = (Member) session.getAttribute("loginMember");

        productService.addQuantity(addproduct, addQuantity, loginMember.getId());

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
    public String updateProduct(@ModelAttribute ProductUpdateDto uproduct, HttpServletRequest request, Model model){
        Map<String, String> errors = new HashMap<>();
        if(!StringUtils.hasText(uproduct.getName())){
            errors.put("name", "제품이름은 필수 항목 입니다.");
        }
        if(uproduct.getPrice()<=0){
            errors.put("price", "가격은 1원 이상으로 입력하세요.");
        }

        if (!errors.isEmpty()) {
            Product product = productService.findById(uproduct.getId());
            model.addAttribute("product",product);
            model.addAttribute("errors",errors);
            return "product/updateProductForm";
        }

        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession(true);
        Member loginMember = (Member) session.getAttribute("loginMember");

        Product product = new Product(uproduct.getId(), uproduct.getCode(), uproduct.getName(),
                uproduct.getPrice(), uproduct.getQuantity(), uproduct.getSell());
        productService.UpdateProduct(product, loginMember.getId());
        return "redirect:/product/update";
    }



    @GetMapping(value = "/update/{productId}")
    public String updateProductForm(@PathVariable Long productId, Model model){
        Product product = productService.findById(productId);
        model.addAttribute("product",product);

        return "product/updateProductForm";
    }

    @GetMapping(value = "/update/detail/{productId}")
    public String detailUpdate(@PathVariable Long productId, Model model){
        List<ProductStateHistory> productStateHistories = productService.getUpdateDetailByPid(productId);
        model.addAttribute("detailUpdates", productStateHistories);

        return "product/productUpdateDetail";
    }

    @PostMapping(value = "/update/state/{productId}")
    public String updateProductSellState(@PathVariable Long productId, HttpServletRequest request){
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession(true);
        Member loginMember = (Member) session.getAttribute("loginMember");

        productService.updateSellState(productId, loginMember.getId());
        return "redirect:/product/update";
    }
}