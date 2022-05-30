package com.example.convenience_pos_system.dao;

import com.example.convenience_pos_system.domain.Product;
import com.example.convenience_pos_system.domain.ProductHistory;
import com.example.convenience_pos_system.domain.ProductStateHistory;
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
public class ProductStateHistoryDao {
    final private JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductStateHistoryDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(ProductStateHistory productStateHistory) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con)
                    throws SQLException {
                // 파라미터로 전달받은 Connection을 이용해서 PreparedStatement 생성
                PreparedStatement pstmt = con.prepareStatement(
                        "insert into PRODUCT_STATE_HISTORY_TB (MID, PID, NAME, NEWNAME, PRICE, NEWPRICE, STATE, DATETIME) " +
                                "values (?, ?, ?, ?, ?, ?, ?, ?)",
                        new String[] { "ID" });
                // 인덱스 파라미터 값 설정
                pstmt.setLong(1, productStateHistory.getMid());
                pstmt.setLong(2, productStateHistory.getPid());
                pstmt.setString(3, productStateHistory.getName());
                pstmt.setString(4, productStateHistory.getNewname());
                pstmt.setInt(5, productStateHistory.getPrice());
                pstmt.setInt(6, productStateHistory.getNewprice());
                pstmt.setString(7, productStateHistory.getState());
                pstmt.setTimestamp(8, Timestamp.valueOf(productStateHistory.getDatetime()));
                // 생성한 PreparedStatement 객체 리턴
                return pstmt;
            }
        }, keyHolder);
        Number keyValue = keyHolder.getKey();
        productStateHistory.setId(keyValue.longValue());
    }

    public List<ProductStateHistory> selectAll() {
        List<ProductStateHistory> results = jdbcTemplate.query("select * from PRODUCT_STATE_HISTORY_TB",
                (ResultSet rs, int rowNum) -> {
                    ProductStateHistory productStateHistory = new ProductStateHistory(
                            rs.getLong("MID"),
                            rs.getLong("PID"),
                            rs.getString("NAME"),
                            rs.getString("NEWNAME"),
                            rs.getInt("PRICE"),
                            rs.getInt("NEWPRICE"),
                            rs.getString("STATE"),
                            rs.getTimestamp("DATETIME").toLocalDateTime());
                    productStateHistory.setId(rs.getLong("ID"));
                    return productStateHistory;
                });
        return results;
    }

    public List<ProductStateHistory> selectByPid(Long pid) {
        List<ProductStateHistory> results = jdbcTemplate.query(
                "select * from PRODUCT_STATE_HISTORY_TB where PID = ?",
                new RowMapper<ProductStateHistory>() {
                    @Override
                    public ProductStateHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ProductStateHistory productStateHistory = new ProductStateHistory(
                                rs.getLong("MID"),
                                rs.getLong("PID"),
                                rs.getString("NAME"),
                                rs.getString("NEWNAME"),
                                rs.getInt("PRICE"),
                                rs.getInt("NEWPRICE"),
                                rs.getString("STATE"),
                                rs.getTimestamp("DATETIME").toLocalDateTime());
                        productStateHistory.setId(rs.getLong("ID"));
                        return productStateHistory;
                    }
                }, pid);

        return results.isEmpty() ? null : results;
    }

    public List<ProductStateHistory> selectByMid(Long mid) {
        List<ProductStateHistory> results = jdbcTemplate.query(
                "select * from PRODUCT_STATE_HISTORY_TB where MID = ? order by DATETIME DESC",
                new RowMapper<ProductStateHistory>() {
                    @Override
                    public ProductStateHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ProductStateHistory productStateHistory = new ProductStateHistory(
                                rs.getLong("MID"),
                                rs.getLong("PID"),
                                rs.getString("NAME"),
                                rs.getString("NEWNAME"),
                                rs.getInt("PRICE"),
                                rs.getInt("NEWPRICE"),
                                rs.getString("STATE"),
                                rs.getTimestamp("DATETIME").toLocalDateTime());
                        productStateHistory.setId(rs.getLong("ID"));
                        return productStateHistory;
                    }
                }, mid);

        return results.isEmpty() ? null : results;
    }
}
