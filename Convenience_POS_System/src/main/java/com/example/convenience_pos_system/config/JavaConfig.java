package com.example.convenience_pos_system.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import com.example.convenience_pos_system.controller.MemberController;
import com.example.convenience_pos_system.controller.ProductController;
import com.example.convenience_pos_system.controller.SaleController;
import com.example.convenience_pos_system.controller.StatisticsController;
import com.example.convenience_pos_system.dao.*;
import com.example.convenience_pos_system.service.MemberService;
import com.example.convenience_pos_system.service.ProductService;
import com.example.convenience_pos_system.service.SaleService;
import com.example.convenience_pos_system.service.StatisticsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class JavaConfig {
    @Bean
    public MemberController memberController(){
        return new MemberController(memberService());
    }

    @Bean
    public ProductController productController(){
        return new ProductController(productService());
    }

    @Bean
    public SaleController saleController(){
        return new SaleController(saleService(), productService());
    }

    @Bean
    public StatisticsController statisticsController(){
        return new StatisticsController(statisticsService());
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberDao(), productHistoryDao(), productStateHistoryDao(), saleDao());
    }

    @Bean
    public ProductService productService(){
        return new ProductService(productDao(), productHistoryDao(), productStateHistoryDao());
    }

    @Bean
    public SaleService saleService(){
        return new SaleService(saleDao(), saleDetailDao(), saleCartDao(), productDao(),
                productHistoryDao());
    }

    @Bean
    public StatisticsService statisticsService(){
        return new StatisticsService(productDao(), saleDao(), saleDetailDao());
    }

    @Bean
    public MemberDao memberDao(){
        return new MemberDao(dataSource());
    }

    @Bean
    public ProductDao productDao() { return new ProductDao(dataSource()); }

    @Bean
    public ProductHistoryDao productHistoryDao() { return new ProductHistoryDao(dataSource()); }

    @Bean
    public ProductStateHistoryDao productStateHistoryDao(){
        return new ProductStateHistoryDao(dataSource());
    }

    @Bean
    public SaleDao saleDao(){
        return new SaleDao(dataSource());
    }

    @Bean
    public SaleDetailDao saleDetailDao(){
        return new SaleDetailDao(dataSource());
    }

    @Bean
    public SaleCartDao saleCartDao(){
        return new SaleCartDao(dataSource());
    }

    @Bean
    public DataSource dataSource() {
        DataSource ds = new DataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://127.0.0.1:3306/POS");
        ds.setUsername("root");
        ds.setPassword("1234");
        ds.setInitialSize(2);
        ds.setMaxActive(10);
        return ds;
    }

    @Bean
    public PlatformTransactionManager transactionManager(){
        DataSourceTransactionManager tm = new DataSourceTransactionManager();
        tm.setDataSource(dataSource());
        return tm;
    }
}
