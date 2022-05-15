package com.example.convenience_pos_system.dao;

import com.example.convenience_pos_system.domain.Product;
import com.example.convenience_pos_system.domain.ProductHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Component
public class ProductHistoryDao {
    final private JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductHistoryDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(ProductHistory productHistory) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con)
                    throws SQLException {
                // 파라미터로 전달받은 Connection을 이용해서 PreparedStatement 생성
                PreparedStatement pstmt = con.prepareStatement(
                        "insert into PRODUCT_HISTORY_TB (PID, MID, NAME, PRICE, QUANTITY, DATETIME, METHOD) " +
                                "values (?, ?, ?, ?, ?, ?, ?)",
                        new String[] { "ID" });
                // 인덱스 파라미터 값 설정
                pstmt.setLong(1, productHistory.getPid());
                pstmt.setLong(2, productHistory.getMid());
                pstmt.setString(3, productHistory.getName());
                pstmt.setInt(4, productHistory.getPrice());
                pstmt.setInt(5, productHistory.getQuantity());
                pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                pstmt.setString(7, productHistory.getMethod());
                // 생성한 PreparedStatement 객체 리턴
                return pstmt;
            }
        }, keyHolder);
        Number keyValue = keyHolder.getKey();
        productHistory.setId(keyValue.longValue());
    }

    public List<ProductHistory> selectByPid(int pid) {
        List<ProductHistory> results = jdbcTemplate.query(
                "select * from PRODUCT_HISTORY_TB where PID = ?",
                new RowMapper<ProductHistory>() {
                    @Override
                    public ProductHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ProductHistory productHistory = new ProductHistory(
                                rs.getLong("PID"),
                                rs.getLong("MID"),
                                rs.getString("NAME"),
                                rs.getInt("PRICE"),
                                rs.getInt("QUANTITY"),
                                rs.getTimestamp("DATETIME").toLocalDateTime(),
                                rs.getString("METHOD"));
                        productHistory.setId(rs.getLong("ID"));
                        return productHistory;
                    }
                }, pid);

        return results.isEmpty() ? null : results;
    }

    public List<ProductHistory> selectByMid(int mid) {
        List<ProductHistory> results = jdbcTemplate.query(
                "select * from PRODUCT_HISTORY_TB where MID = ?",
                new RowMapper<ProductHistory>() {
                    @Override
                    public ProductHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ProductHistory productHistory = new ProductHistory(
                                rs.getLong("PID"),
                                rs.getLong("MID"),
                                rs.getString("NAME"),
                                rs.getInt("PRICE"),
                                rs.getInt("QUANTITY"),
                                rs.getTimestamp("DATETIME").toLocalDateTime(),
                                rs.getString("METHOD"));
                        productHistory.setId(rs.getLong("ID"));
                        return productHistory;
                    }
                }, mid);

        return results.isEmpty() ? null : results;
    }
}
