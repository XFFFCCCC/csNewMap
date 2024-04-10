package com.pcl.healthism.dao.jdbc;

import io.netty.util.internal.ResourcesUtil;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.annotation.PostConstruct;
import java.io.*;

@Service
public class InitDB {

    private final JdbcTemplate jdbcTemplate;

    public InitDB(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    private void init() throws Exception {
        ClassPathResource resource = new ClassPathResource("sql" + File.separator + "all_tables.sql");
        InputStream inputStream = resource.getInputStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(2048);
        FileCopyUtils.copy(inputStream, bos);
        inputStream.close();

        String sql = bos.toString("UTF-8");
        jdbcTemplate.execute(sql);
    }

}
