package com.example.convenience_pos_system.dao;

import com.example.convenience_pos_system.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class ProductDao {
    final private JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(Product product) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con)
                    throws SQLException {
                // 파라미터로 전달받은 Connection을 이용해서 PreparedStatement 생성
                PreparedStatement pstmt = con.prepareStatement(
                        "insert into PRODUCT_TB (CODE, NAME, PRICE, QUANTITY) " +
                                "values (?, ?, ?, ?)",
                        new String[] { "ID" });
                // 인덱스 파라미터 값 설정
                pstmt.setString(1, product.getCode());
                pstmt.setString(2, product.getName());
                pstmt.setInt(3, product.getPrice());
                pstmt.setInt(4, product.getQuantity());
                // 생성한 PreparedStatement 객체 리턴
                return pstmt;
            }
        }, keyHolder);
        Number keyValue = keyHolder.getKey();
        product.setId(keyValue.longValue());
    }

    public void update(Product product) {
        jdbcTemplate.update(
                "update PRODUCT_TB set NAME = ?, PRICE = ?, QUANTITY = ? where CODE = ?",
                product.getName(), product.getPrice(), product.getQuantity(), product.getCode());
    }

    public Product selectByCode(String code) {
        List<Product> results = jdbcTemplate.query(
                "select * from PRODUCT_TB where CODE = ?",
                new RowMapper<Product>() {
                    @Override
                    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Product member = new Product(
                                rs.getString("CODE"),
                                rs.getString("NAME"),
                                rs.getInt("PRICE"),
                                rs.getInt("QUANTITY"));
                        member.setId(rs.getLong("ID"));
                        return member;
                    }
                }, code);

        return results.isEmpty() ? null : results.get(0);
    }

    public List<Product> selectAll() {
        List<Product> results = jdbcTemplate.query("select * from PRODUCT_TB",
                (ResultSet rs, int rowNum) -> {
                    Product product = new Product(
                            rs.getString("CODE"),
                            rs.getString("NAME"),
                            rs.getInt("PRICE"),
                            rs.getInt("QUANTITY"));
                    product.setId(rs.getLong("ID"));
                    return product;
                });
        return results;
    }

    public int count() {
        Integer count = jdbcTemplate.queryForObject(
                "select count(*) from PRODUCT", Integer.class);
        return count;
    }
}
