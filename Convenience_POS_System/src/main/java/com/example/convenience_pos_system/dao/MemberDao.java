package com.example.convenience_pos_system.dao;

import com.example.convenience_pos_system.domain.Member;
import com.example.convenience_pos_system.domain.Product;
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
public class MemberDao {
    final private JdbcTemplate jdbcTemplate;

    @Autowired
    public MemberDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Member selectByEmail(String email) {
        List<Member> results = jdbcTemplate.query(
                "select * from MEMBER_TB where EMAIL = ?",
                new RowMapper<Member>() {
                    @Override
                    public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Member member = new Member(
                                rs.getString("EMAIL"),
                                rs.getString("PASSWORD"),
                                rs.getString("NAME"),
                                rs.getString("ROLE"),
                                rs.getTimestamp("REGDATE"));
                        member.setId(rs.getLong("ID"));
                        return member;
                    }
                }, email);

        return results.isEmpty() ? null : results.get(0);
    }

    public Member selectById(Long id) {
        List<Member> results = jdbcTemplate.query(
                "select * from MEMBER_TB where ID = ?",
                new RowMapper<Member>() {
                    @Override
                    public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Member member = new Member(
                                rs.getString("EMAIL"),
                                rs.getString("PASSWORD"),
                                rs.getString("NAME"),
                                rs.getString("ROLE"),
                                rs.getTimestamp("REGDATE"));
                        member.setId(rs.getLong("ID"));
                        return member;
                    }
                }, id);
        return results.isEmpty() ? null : results.get(0);
    }

    public void insert(final Member member) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection con)
                            throws SQLException
                    {
                        PreparedStatement pstmt = con.prepareStatement(
                                "insert into MEMBER_TB (EMAIL, PASSWORD, NAME , ROLE , REGDATE) values (?, ?, ?, ?, ?)",
                                new String[] {"ID"} );
                        pstmt.setString(1, member.getEmail());
                        pstmt.setString(2, member.getPassword());
                        pstmt.setString(3, member.getName());
                        pstmt.setString(4, member.getRole());
                        pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                        return pstmt;
                    }
                },
                keyHolder );
        Number keyValue = keyHolder.getKey();
        member.setId(keyValue.longValue());
    }

    public void update(Member member) {
        jdbcTemplate.update(
                "update MEMBER_TB set NAME = ?, PASSWORD = ?, ROLE = ? where EMAIL = ?",
                member.getName(), member.getPassword(), member.getRole(), member.getEmail());
    }

    public List<Member> selectAll() {
        List<Member> results = jdbcTemplate.query("select * from MEMBER_TB",
                (ResultSet rs, int rowNum) -> {
                    Member member = new Member(
                            rs.getString("EMAIL"),
                            rs.getString("PASSWORD"),
                            rs.getString("NAME"),
                            rs.getString("ROLE"),
                            rs.getTimestamp("REGDATE"));
                    member.setId(rs.getLong("ID"));
                    return member;
                });
        return results;
    }
}
