package com.example.convenience_pos_system.service;

import com.example.convenience_pos_system.dao.ProductDao;
import com.example.convenience_pos_system.dao.SaleDao;
import com.example.convenience_pos_system.dao.SaleDetailDao;
import com.example.convenience_pos_system.domain.Sale;
import com.example.convenience_pos_system.domain.SaleDetail;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
