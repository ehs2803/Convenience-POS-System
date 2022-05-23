package com.example.convenience_pos_system.controller;

import com.example.convenience_pos_system.dao.ajax.AjaxProductQuantityPerDay;
import com.example.convenience_pos_system.dao.ajax.AjaxResponseQuantityPerDay;
import com.example.convenience_pos_system.domain.*;
import com.example.convenience_pos_system.service.StatisticsService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

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

    @ResponseBody
    @PostMapping(value = "/sale/day")
    public String getSaleDay(@RequestParam Map<String, Object> params)
            throws IOException {
        String date = (String) params.get("datetime");
        List<Sale> sales = statisticsService.getSaleByDate(date);
        List<SaleDetail> saleDetails = new ArrayList<>();

        JsonObject jo = new JsonObject();

        if(sales==null){
            jo.addProperty("status","NO");
        }
        else{
            for(int i=0;i<sales.size();i++){
                Long sid = sales.get(i).getId();
                List<SaleDetail> saleDetailList = statisticsService.getSaleDetailsById(sid);
                saleDetails.addAll(saleDetailList);
            }

            Map<Long, Integer> quantityDay = new HashMap<>();
            for(int i=0;i<saleDetails.size();i++){
                Long pid = saleDetails.get(i).getPid();
                int quantity = saleDetails.get(i).getQuantity();
                if(quantityDay.containsKey(pid)){
                    quantityDay.put(pid, quantityDay.get(pid)+quantity);
                }
                else{
                    quantityDay.put(pid, quantity);
                }
            }

            List<Long> listKeySet = new ArrayList<>(quantityDay.keySet());
            Collections.sort(listKeySet,
                    (value1, value2) -> (quantityDay.get(value2).compareTo(quantityDay.get(value1))));
            for(Long key : listKeySet) { System.out.println("key : " + key + " , " + "value : " + quantityDay.get(key)); }

            List<AjaxProductQuantityPerDay> temp = new ArrayList<>();
            for(Long key : listKeySet){
                Product product = statisticsService.getProductByPid(key);
                AjaxProductQuantityPerDay tempElement = new AjaxProductQuantityPerDay(key, product.getCode(),
                        product.getName(), product.getPrice(), quantityDay.get(key));
                temp.add(tempElement);
            }

            jo.addProperty("status","YES");
            JsonArray ja = new JsonArray();
            for(Long key : listKeySet){
                Product product = statisticsService.getProductByPid(key);

                JsonObject jObj = new JsonObject();
                jObj.addProperty("pid", key);
                jObj.addProperty("code", product.getCode());
                jObj.addProperty("name", product.getName());
                jObj.addProperty("price", product.getPrice());
                jObj.addProperty("SellQuantity", quantityDay.get(key));
                ja.add(jObj);
            }
            jo.add("products", ja);



        }

        return jo.toString();
        //return new ResponseEntity<>(QuantityPerDay, HttpStatus.OK);
    }
}
