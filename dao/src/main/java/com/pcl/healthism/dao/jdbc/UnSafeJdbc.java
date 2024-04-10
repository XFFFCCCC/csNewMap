package com.pcl.healthism.dao.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * FBI WARNING : NOT SAFE
 * 后门更新DB数据，不建议在业务逻辑使用，无任何sql防注入逻辑
 */
@Component
public class UnSafeJdbc {
    private final JdbcTemplate jdbcTemplate;

    public UnSafeJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void execute(String sql) {
        jdbcTemplate.execute(sql);
    }
}
