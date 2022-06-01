package com.example.convenience_pos_system.controller;

import com.example.convenience_pos_system.domain.*;
import com.example.convenience_pos_system.service.StatisticsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;
import java.util.*;

@Controller
@RequestMapping(value = "/statistics")
public class StatisticsController {
    final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping(value = "")
    public String statistics(HttpServletRequest request, Model model){
        HttpSession session = request.getSession(true);
        Member loginMember = (Member) session.getAttribute("loginMember");

        List<Sale> sales = statisticsService.getAllSales();

        model.addAttribute("sales", sales);
        model.addAttribute("loginMember",loginMember);

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

    @ResponseBody
    @PostMapping(value = "/sale/day")
    public String getSaleDay(@RequestParam Map<String, Object> params)
            throws IOException {
        String date = (String) params.get("datetime");
        String mode = (String) params.get("mode");

        return statisticsService.getStatisticData(date, mode,"day");
    }

    @ResponseBody
    @PostMapping(value = "/sale/week")
    public String getSaleWeek(@RequestParam Map<String, Object> params)
            throws IOException {
        String date = (String) params.get("datetime");
        String mode = (String) params.get("mode");

        String [] splits = date.split("-");
        int year = Integer.parseInt(splits[0]);
        int weekNumber = Integer.parseInt(splits[1].substring(1));

        LocalDate week = LocalDate.of(year,2,1).with(ChronoField.ALIGNED_WEEK_OF_YEAR, weekNumber);
        LocalDate startLD = week.with(DayOfWeek.MONDAY);
        String start = week.with(DayOfWeek.MONDAY).toString();
        String end = startLD.plusDays(6).toString();
        String weekDate = start+":"+end;

        return statisticsService.getStatisticData(weekDate, mode,"week");
    }

    @ResponseBody
    @PostMapping(value = "/sale/month")
    public String getSaleMonth(@RequestParam Map<String, Object> params)
            throws IOException {
        String date = (String) params.get("datetime");
        String mode = (String) params.get("mode");

        return statisticsService.getStatisticData(date, mode,"month");
    }
}
