package com.example.convenience_pos_system.dao;

import com.example.convenience_pos_system.domain.Product;
import com.example.convenience_pos_system.domain.Sale;
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
public class SaleDao {
    final private JdbcTemplate jdbcTemplate;

    @Autowired
    public SaleDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(Sale sale) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con)
                    throws SQLException {
                // 파라미터로 전달받은 Connection을 이용해서 PreparedStatement 생성
                PreparedStatement pstmt = con.prepareStatement(
                        "insert into SALE_TB (MID, PRICE, DATETIME) " +
                                "values (?, ?, ?)",
                        new String[] { "ID" });
                // 인덱스 파라미터 값 설정
                pstmt.setLong(1, sale.getMid());
                pstmt.setInt(2, sale.getPrice());
                pstmt.setTimestamp(3, Timestamp.valueOf(sale.getDatetime()));
                // 생성한 PreparedStatement 객체 리턴
                return pstmt;
            }
        }, keyHolder);
        Number keyValue = keyHolder.getKey();
        sale.setId(keyValue.longValue());
    }

    public List<Sale> selectByMid(Long mid) {
        List<Sale> results = jdbcTemplate.query(
                "select * from SALE_TB where MID = ?",
                new RowMapper<Sale>() {
                    @Override
                    public Sale mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Sale sale = new Sale(
                                rs.getLong("MID"),
                                rs.getInt("PRICE"),
                                rs.getTimestamp("DATETIME").toLocalDateTime());
                        sale.setId(rs.getLong("ID"));
                        return sale;
                    }
                }, mid);

        return results.isEmpty() ? null : results;
    }

    public Sale selectById(Long id) {
        List<Sale> results = jdbcTemplate.query(
                "select * from SALE_TB where ID = ?",
                new RowMapper<Sale>() {
                    @Override
                    public Sale mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Sale sale = new Sale(
                                rs.getLong("MID"),
                                rs.getInt("PRICE"),
                                rs.getTimestamp("DATETIME").toLocalDateTime());
                        sale.setId(rs.getLong("ID"));
                        return sale;
                    }
                }, id);

        return results.isEmpty() ? null : results.get(0);
    }

    public List<Sale> selectAll() {
        List<Sale> results = jdbcTemplate.query("select * from SALE_TB",
                (ResultSet rs, int rowNum) -> {
                    Sale sale = new Sale(
                            rs.getLong("MID"),
                            rs.getInt("PRICE"),
                            rs.getTimestamp("DATETIME").toLocalDateTime());
                    sale.setId(rs.getLong("ID"));
                    return sale;
                });
        return results;
    }
}
