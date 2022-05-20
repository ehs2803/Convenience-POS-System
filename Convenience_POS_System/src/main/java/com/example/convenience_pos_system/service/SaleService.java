package com.example.convenience_pos_system.service;

import com.example.convenience_pos_system.dao.SaleCartDao;
import com.example.convenience_pos_system.dao.SaleDao;
import com.example.convenience_pos_system.dao.SaleDetailDao;
import org.springframework.stereotype.Service;

@Service
public class SaleService {
    final SaleDao saleDao;
    final SaleDetailDao saleDetailDao;
    final SaleCartDao saleCartDao;

    public SaleService(SaleDao saleDao, SaleDetailDao saleDetailDao, SaleCartDao saleCartDao) {
        this.saleDao = saleDao;
        this.saleDetailDao = saleDetailDao;
        this.saleCartDao = saleCartDao;
    }
}
