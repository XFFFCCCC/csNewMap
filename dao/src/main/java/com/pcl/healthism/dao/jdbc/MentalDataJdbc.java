package com.pcl.healthism.dao.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MentalDataJdbc {
    private final JdbcTemplate jdbcTemplate;

    public void queryAllUserScore(RowCallbackHandler handler) {
        String sql = "select `user_id`, `precise_answer`, `timestamp` from mental_answer where id > 0 and precise_answer != '' order  by id desc";
        jdbcTemplate.query(sql, handler);
    }

    public void queryDistrict(List<Long> userIds, RowCallbackHandler handler) {
        String pattern = "select user_id, district from mental_userinfo where user_id in (%s)";
        String sql = String.format(pattern, userIds.stream().map(Object::toString).collect(Collectors.joining(",")));
        jdbcTemplate.query(sql, handler);
    }
}
