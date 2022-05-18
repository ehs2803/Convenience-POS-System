package com.example.convenience_pos_system.service;

import com.example.convenience_pos_system.dao.MemberDao;
import com.example.convenience_pos_system.dao.ProductDao;
import com.example.convenience_pos_system.dao.ProductHistoryDao;
import com.example.convenience_pos_system.dao.ProductStateHistoryDao;
import com.example.convenience_pos_system.domain.Product;
import com.example.convenience_pos_system.domain.ProductHistory;
import com.example.convenience_pos_system.domain.ProductStateHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {
    final ProductDao productDao;
    final ProductHistoryDao productHistoryDao;
    final ProductStateHistoryDao productStateHistoryDao;

    @Autowired
    public ProductService(ProductDao productDao, ProductHistoryDao productHistoryDao,
                          ProductStateHistoryDao productStateHistoryDao) {
        this.productDao = productDao;
        this.productHistoryDao = productHistoryDao;
        this.productStateHistoryDao = productStateHistoryDao;
    }

    public List<Product> findAll(){
        return productDao.selectAll();
    }

    public Product findById(Long id){
        return productDao.selectById(id);
    }

    public List<ProductHistory> findProductHistoryById(Long id){
        return productHistoryDao.selectByPid(id);
    }

    public void addNewProduct(Product product, Long mid){
        LocalDateTime currentDateTime = LocalDateTime.now();
        productDao.insert(product);

        ProductHistory productHistory = new ProductHistory(mid,product.getName(),
                product.getPrice(), product.getQuantity(),"BUY");
        productHistory.setPid(product.getId());
        productHistory.setTime(currentDateTime);
        productHistoryDao.insert(productHistory);

        ProductStateHistory productStateHistory = new ProductStateHistory(mid, product.getId(),
                product.getName(), product.getName(), product.getPrice(), product.getPrice(),
                "CREATE", currentDateTime);
        productStateHistoryDao.insert(productStateHistory);
    }

    public void addQuantity(Product product, int addQuantity, Long mid){
        productDao.update(product);

        LocalDateTime currentDateTime = LocalDateTime.now();
        ProductHistory productHistory = new ProductHistory(mid,product.getName(),
                product.getPrice(), addQuantity,"BUY");
        productHistory.setTime(currentDateTime);
        productHistory.setPid(product.getId());
        productHistoryDao.insert(productHistory);
    }

    public void UpdateProduct(Product product, Long mid){
        LocalDateTime currentDateTime = LocalDateTime.now();

        Product oldProduct = productDao.selectById(product.getId());

        productDao.update(product);

        ProductStateHistory productStateHistory = new ProductStateHistory(mid, product.getId(),
                oldProduct.getName(), product.getName(), oldProduct.getPrice(), product.getPrice(),
                "UPDATE", currentDateTime);
        productStateHistoryDao.insert(productStateHistory);
    }

    public void updateSellState(Long pid, Long mid){
        LocalDateTime currentDateTime = LocalDateTime.now();

        Product product = productDao.selectById(pid);
        if(product.getSell()==1){
            productDao.updateSellState(pid,0);
            ProductStateHistory productStateHistory = new ProductStateHistory(mid, product.getId(),
                    product.getName(), product.getName(), product.getPrice(), product.getPrice(),
                    "STOP", currentDateTime);
            productStateHistoryDao.insert(productStateHistory);
        }
        else{
            productDao.updateSellState(pid,1);
            ProductStateHistory productStateHistory = new ProductStateHistory(mid, product.getId(),
                    product.getName(), product.getName(), product.getPrice(), product.getPrice(),
                    "START", currentDateTime);
            productStateHistoryDao.insert(productStateHistory);
        }
    }

    public List<ProductStateHistory> getUpdateDetailByPid(Long pid){
        return productStateHistoryDao.selectByPid(pid);
    }
}
