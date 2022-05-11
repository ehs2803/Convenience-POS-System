package com.example.convenience_pos_system.dao;

import com.example.convenience_pos_system.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

@Component
public class MemberDao {
    final private JdbcTemplate jdbcTemplate;

    @Autowired
    public MemberDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
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
