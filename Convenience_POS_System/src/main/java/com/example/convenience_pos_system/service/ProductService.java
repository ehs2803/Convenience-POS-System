package com.example.convenience_pos_system.service;

import com.example.convenience_pos_system.dao.MemberDao;
import com.example.convenience_pos_system.dao.ProductDao;
import com.example.convenience_pos_system.dao.ProductHistoryDao;
import com.example.convenience_pos_system.domain.Product;
import com.example.convenience_pos_system.domain.ProductHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ProductService {
    final ProductDao productDao;
    final ProductHistoryDao productHistoryDao;

    @Autowired
    public ProductService(ProductDao productDao, ProductHistoryDao productHistoryDao) {
        this.productDao = productDao;
        this.productHistoryDao = productHistoryDao;
    }

    public List<Product> findAll(){
        return productDao.selectAll();
    }

    public Product findById(Long id){
        return productDao.selectById(id);
    }

    public void addNewProduct(Product product, Long mid){
        ProductHistory productHistory = new ProductHistory(mid,product.getName(),
                product.getPrice(), product.getQuantity(),"BUY");
        productDao.insert(product);
        productHistory.setPid(product.getId());
        productHistoryDao.insert(productHistory);
    }

    public void addQuantity(Product product, int addQuantity, Long mid){
        ProductHistory productHistory = new ProductHistory(mid,product.getName(),
                product.getPrice(), addQuantity,"BUY");
        productDao.update(product);
        productHistory.setPid(product.getId());
        productHistoryDao.insert(productHistory);
    }

    public void UpdateProduct(){

    }

}
