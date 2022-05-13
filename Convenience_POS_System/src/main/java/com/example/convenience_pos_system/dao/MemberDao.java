package com.example.convenience_pos_system.dao;

import com.example.convenience_pos_system.domain.Member;
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
                "select * from member where email = ?",
                new RowMapper<Member>() {
                    @Override
                    public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Member member = new Member(
                                rs.getString("email"),
                                rs.getString("password"),
                                rs.getString("name"),
                                rs.getTimestamp("regdate").toString());
                        member.setId(rs.getLong("id"));
                        return member;
                    }
                }, email);

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
                                "insert into MEMBER (email, password, name , role , regdate) values (?, ?, ?, ?, ?)",
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
}
