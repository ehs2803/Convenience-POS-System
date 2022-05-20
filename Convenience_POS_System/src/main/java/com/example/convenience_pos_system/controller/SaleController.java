package com.example.convenience_pos_system.controller;

import com.example.convenience_pos_system.service.SaleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/sale")
public class SaleController {
    final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }
}
