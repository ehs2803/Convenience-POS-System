package com.example.convenience_pos_system.dao;

import com.example.convenience_pos_system.domain.Product;
import com.example.convenience_pos_system.domain.SaleCart;
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
public class SaleCartDao {
    final private JdbcTemplate jdbcTemplate;

    @Autowired
    public SaleCartDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(SaleCart saleCart) {
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con)
                    throws SQLException {
                // 파라미터로 전달받은 Connection을 이용해서 PreparedStatement 생성
                PreparedStatement pstmt = con.prepareStatement(
                        "insert into SALE_CART_TB (MID, PID, QUANTITY) values (?, ?, ?)");
                // 인덱스 파라미터 값 설정
                pstmt.setLong(1, saleCart.getMid());
                pstmt.setLong(2, saleCart.getPid());
                pstmt.setInt(3, saleCart.getQuantity());
                // 생성한 PreparedStatement 객체 리턴
                return pstmt;
            }
        });
    }

    public SaleCart selectByPK(Long mid, Long pid) {
        List<SaleCart> results = jdbcTemplate.query(
                "select * from SALE_CART_TB where MID = ? and PID = ?",
                new RowMapper<SaleCart>() {
                    @Override
                    public SaleCart mapRow(ResultSet rs, int rowNum) throws SQLException {
                        SaleCart saleCart = new SaleCart(
                                rs.getLong("MID"),
                                rs.getLong("PID"),
                                rs.getInt("QUANTITY"));
                        return saleCart ;
                    }
                }, mid, pid);

        return results.isEmpty() ? null : results.get(0);
    }

    public List<SaleCart> selectByMid(Long mid) {
        List<SaleCart> results = jdbcTemplate.query(
                "select * from SALE_CART_TB where MID = ?",
                new RowMapper<SaleCart>() {
                    @Override
                    public SaleCart mapRow(ResultSet rs, int rowNum) throws SQLException {
                        SaleCart saleCart = new SaleCart(
                                rs.getLong("MID"),
                                rs.getLong("PID"),
                                rs.getInt("QUANTITY"));
                        return saleCart ;
                    }
                }, mid);

        return results.isEmpty() ? null : results;
    }

    public void update(SaleCart saleCart) {
        jdbcTemplate.update(
                "update SALE_CART_TB set QUANTITY = ? where MID = ? and PID = ?",
                saleCart.getQuantity(), saleCart.getMid(), saleCart.getPid());
    }

    public void delete(Long mid, Long pid) {
        jdbcTemplate.update(
                "delete from SALE_CART_TB where MID = ? and PID = ?",
                mid, pid);
    }

    public void delete(Long mid){
        jdbcTemplate.update(
                "delete from SALE_CART_TB where MID = ?",
                mid);
    }
}
