package com.example.convenience_pos_system.controller;

import com.example.convenience_pos_system.domain.*;
import com.example.convenience_pos_system.service.StatisticsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/statistics")
public class StatisticsController {
    final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping(value = "")
    public String statistics(Model model){

        List<Sale> sales = statisticsService.getAllSales();

        model.addAttribute("sales", sales);

        return "statistics/statistics";
    }

    @GetMapping(value = "/sale/{saleId}")
    public String saleDetail(@PathVariable Long saleId, Model model){

        List<SaleDetail> saleDetails = statisticsService.getSaleDetailsById(saleId);
        Sale sale = statisticsService.getSaleById(saleId);

        model.addAttribute("saleDetails", saleDetails);
        model.addAttribute("sale", sale);

        return "statistics/saleDetail";
    }
}
