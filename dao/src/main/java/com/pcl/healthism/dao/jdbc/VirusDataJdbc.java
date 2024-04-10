package com.pcl.healthism.dao.jdbc;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class VirusDataJdbc {
    private final JdbcTemplate jdbcTemplate;

    public List<Statistic> queryStatistic(long begin , long end) {
        String pattern = "select contributory, count(*) as cnt from ncov_news where add_timestamp_mils between %d and %d group by contributory";
        String sql = String.format(pattern, begin, end);
        return jdbcTemplate.query(sql, (row, idx) -> {
            Statistic item = new Statistic();
            item.setName(row.getString("contributory"));
            item.setCount(row.getInt("cnt"));
            return item;
        });
    }



    @Data
    public static class Statistic{
        private String name;
        private int count;
    }
}
