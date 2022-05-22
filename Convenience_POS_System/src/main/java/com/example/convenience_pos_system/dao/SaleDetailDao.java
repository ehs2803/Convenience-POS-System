package com.example.convenience_pos_system.dao;

import com.example.convenience_pos_system.domain.Sale;
import com.example.convenience_pos_system.domain.SaleDetail;
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
public class SaleDetailDao {
    final private JdbcTemplate jdbcTemplate;

    @Autowired
    public SaleDetailDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(SaleDetail saleDetail) {
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con)
                    throws SQLException {
                // 파라미터로 전달받은 Connection을 이용해서 PreparedStatement 생성
                PreparedStatement pstmt = con.prepareStatement(
                        "insert into SALE_DETAIL_TB (SID, PID, NAME, PRICE, QUANTITY) values (?, ?, ?, ?, ?)");
                // 인덱스 파라미터 값 설정
                pstmt.setLong(1, saleDetail.getSid());
                pstmt.setLong(2, saleDetail.getPid());
                pstmt.setString(3, saleDetail.getName());
                pstmt.setInt(4, saleDetail.getPrice());
                pstmt.setInt(5, saleDetail.getQuantity());
                // 생성한 PreparedStatement 객체 리턴
                return pstmt;
            }
        });
    }

    public List<SaleDetail> selectBySid(Long sid) {
        List<SaleDetail> results = jdbcTemplate.query(
                "select * from SALE_DETAIL_TB where SID = ?",
                new RowMapper<SaleDetail>() {
                    @Override
                    public SaleDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
                        SaleDetail saleDetail = new SaleDetail(
                                rs.getLong("SID"),
                                rs.getLong("PID"),
                                rs.getString("NAME"),
                                rs.getInt("PRICE"),
                                rs.getInt("QUANTITY"));
                        return saleDetail;
                    }
                }, sid);

        return results.isEmpty() ? null : results;
    }
}
