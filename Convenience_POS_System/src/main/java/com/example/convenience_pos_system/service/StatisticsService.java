package com.example.convenience_pos_system.service;

import com.example.convenience_pos_system.dao.ProductDao;
import com.example.convenience_pos_system.dao.SaleDao;
import com.example.convenience_pos_system.dao.SaleDetailDao;
import com.example.convenience_pos_system.dao.ajax.AjaxProductQuantityPerDay;
import com.example.convenience_pos_system.domain.Product;
import com.example.convenience_pos_system.domain.Sale;
import com.example.convenience_pos_system.domain.SaleDetail;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StatisticsService {
    final ProductDao productDao;
    final SaleDao saleDao;
    final SaleDetailDao saleDetailDao;

    public StatisticsService(ProductDao productDao, SaleDao saleDao, SaleDetailDao saleDetailDao) {
        this.productDao = productDao;
        this.saleDao = saleDao;
        this.saleDetailDao = saleDetailDao;
    }

    public List<Sale> getAllSales(){
        return saleDao.selectAll();
    }

    public List<SaleDetail> getSaleDetailsById(Long sid){
        return saleDetailDao.selectBySid(sid);
    }

    public Sale getSaleById(Long id){
        return saleDao.selectById(id);
    }

    public String getStatisticData(String date, String mode, String per) {
        List<Sale> sales = new ArrayList<>();
        if(per.equals("day")) sales = saleDao.selectByDay(date);
        else if(per.equals("month")) sales = saleDao.selectByMonth(date);
        else{
            String start = date.split(":")[0];
            String end = date.split(":")[1];
            sales = saleDao.selectByWeek(start, end);
        }

        List<SaleDetail> saleDetails = new ArrayList<>();

        JsonObject jo = new JsonObject();

        if (sales == null) {
            jo.addProperty("status", "NO");
            if(per.equals("week")){
                jo.addProperty("start", date.split(":")[0]);
                jo.addProperty("end", date.split(":")[1]);
            }
        } else {
            for (int i = 0; i < sales.size(); i++) {
                Long sid = sales.get(i).getId();
                List<SaleDetail> saleDetailList = saleDetailDao.selectBySid(sid);
                saleDetails.addAll(saleDetailList);
            }

            Map<Long, Integer> quantityDay = new HashMap<>();
            if(mode.equals("quantity")){
                for (int i = 0; i < saleDetails.size(); i++) {
                    Long pid = saleDetails.get(i).getPid();
                    int quantity = saleDetails.get(i).getQuantity();
                    if (quantityDay.containsKey(pid)) {
                        quantityDay.put(pid, quantityDay.get(pid) + quantity);
                    } else {
                        quantityDay.put(pid, quantity);
                    }
                }
            }
            else if(mode.equals("price")){
                for (int i = 0; i < saleDetails.size(); i++) {
                    Long pid = saleDetails.get(i).getPid();
                    int quantity = saleDetails.get(i).getQuantity();
                    int price = saleDetails.get(i).getPrice();
                    if (quantityDay.containsKey(pid)) {
                        quantityDay.put(pid, quantityDay.get(pid) + quantity*price);
                    } else {
                        quantityDay.put(pid, quantity*price);
                    }
                }
            }

            List<Long> listKeySet = new ArrayList<>(quantityDay.keySet());
            Collections.sort(listKeySet,
                    (value1, value2) -> (quantityDay.get(value2).compareTo(quantityDay.get(value1))));
            //for(Long key : listKeySet) { System.out.println("key : " + key + " , " + "value : " + quantityDay.get(key)); }

            List<AjaxProductQuantityPerDay> temp = new ArrayList<>();
            for (Long key : listKeySet) {
                Product product = productDao.selectById(key);
                AjaxProductQuantityPerDay tempElement = new AjaxProductQuantityPerDay(key, product.getCode(),
                        product.getName(), product.getPrice(), quantityDay.get(key));
                temp.add(tempElement);
            }

            jo.addProperty("status", "YES");
            if(per.equals("week")){
                jo.addProperty("start", date.split(":")[0]);
                jo.addProperty("end", date.split(":")[1]);
            }
            JsonArray ja = new JsonArray();
            for (Long key : listKeySet) {
                Product product = productDao.selectById(key);

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
    }

    public String getPriceData(String date, String per) {
        List<Sale> sales = new ArrayList<>();
        if(per.equals("day")) sales = saleDao.selectByDay(date);
        else if(per.equals("month")) sales = saleDao.selectByMonth(date);
        else{
            String start = date.split(":")[0];
            String end = date.split(":")[1];
            sales = saleDao.selectByWeek(start, end);
        }

        List<SaleDetail> saleDetails = new ArrayList<>();

        JsonObject jo = new JsonObject();

        if (sales == null) {
            jo.addProperty("status", "NO");
            if(per.equals("week")){
                jo.addProperty("start", date.split(":")[0]);
                jo.addProperty("end", date.split(":")[1]);
            }
        } else {
            for (int i = 0; i < sales.size(); i++) {
                Long sid = sales.get(i).getId();
                List<SaleDetail> saleDetailList = saleDetailDao.selectBySid(sid);
                saleDetails.addAll(saleDetailList);
            }

            Map<Long, Integer> quantityDay = new HashMap<>();
            for (int i = 0; i < saleDetails.size(); i++) {
                Long pid = saleDetails.get(i).getPid();
                int quantity = saleDetails.get(i).getQuantity();
                if (quantityDay.containsKey(pid)) {
                    quantityDay.put(pid, quantityDay.get(pid) + quantity);
                } else {
                    quantityDay.put(pid, quantity);
                }
            }

            List<Long> listKeySet = new ArrayList<>(quantityDay.keySet());
            Collections.sort(listKeySet,
                    (value1, value2) -> (quantityDay.get(value2).compareTo(quantityDay.get(value1))));
            //for(Long key : listKeySet) { System.out.println("key : " + key + " , " + "value : " + quantityDay.get(key)); }

            List<AjaxProductQuantityPerDay> temp = new ArrayList<>();
            for (Long key : listKeySet) {
                Product product = productDao.selectById(key);
                AjaxProductQuantityPerDay tempElement = new AjaxProductQuantityPerDay(key, product.getCode(),
                        product.getName(), product.getPrice(), quantityDay.get(key));
                temp.add(tempElement);
            }

            jo.addProperty("status", "YES");
            if(per.equals("week")){
                jo.addProperty("start", date.split(":")[0]);
                jo.addProperty("end", date.split(":")[1]);
            }
            JsonArray ja = new JsonArray();
            for (Long key : listKeySet) {
                Product product = productDao.selectById(key);

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
    }
}
