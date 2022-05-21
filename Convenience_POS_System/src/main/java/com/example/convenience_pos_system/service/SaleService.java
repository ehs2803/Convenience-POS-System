package com.example.convenience_pos_system.service;

import com.example.convenience_pos_system.dao.*;
import com.example.convenience_pos_system.domain.Product;
import com.example.convenience_pos_system.domain.SaleCart;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService {
    final SaleDao saleDao;
    final SaleDetailDao saleDetailDao;
    final SaleCartDao saleCartDao;
    final ProductDao productDao;
    final ProductHistoryDao productHistoryDao;

    public SaleService(SaleDao saleDao, SaleDetailDao saleDetailDao, SaleCartDao saleCartDao, ProductDao productDao,
                       ProductHistoryDao productHistoryDao) {
        this.saleDao = saleDao;
        this.saleDetailDao = saleDetailDao;
        this.saleCartDao = saleCartDao;
        this.productDao = productDao;
        this.productHistoryDao = productHistoryDao;
    }

    public void addCart(Long mid, Long pid, int quantity){
        SaleCart saleCart = saleCartDao.selectByPK(mid,pid);
        if(saleCart==null){
            SaleCart addSaleCart = new SaleCart(mid,pid,quantity);
            saleCartDao.insert(addSaleCart);
        }
        else{
            int _quantity = saleCart.getQuantity();
            saleCart.setQuantity(_quantity+quantity);
            saleCartDao.update(saleCart);
        }

        Product product = productDao.selectById(pid);
        product.setQuantity(product.getQuantity()-quantity);
        productDao.update(product);
    }

    public List<SaleCart> getAllCartByMid(Long mid){
        return saleCartDao.selectByMid(mid);
    }

    public void deleteSaleCart(Long mid, Long pid){
        // get
        SaleCart saleCart = saleCartDao.selectByPK(mid,pid);
        // product add
        Product product = productDao.selectById(pid);
        product.setQuantity(product.getQuantity()+saleCart.getQuantity());
        productDao.update(product);
        //delete
        saleCartDao.delete(mid, pid);
    }

    public void deleteAllSaleCart(Long mid){
        List<SaleCart> saleCartList = saleCartDao.selectByMid(mid);
        for(int i=0;i<saleCartList.size();i++){
            Product product = productDao.selectById(saleCartList.get(i).getPid());
            product.setQuantity(product.getQuantity()+saleCartList.get(i).getQuantity());
            productDao.update(product);
        }
        saleCartDao.delete(mid);
    }

    public void sale(){

    }
}
