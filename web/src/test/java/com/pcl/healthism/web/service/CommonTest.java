package com.pcl.healthism.web.service;

import com.google.common.collect.Lists;
import com.pcl.healthism.bussiness.common.Pair;
import com.pcl.healthism.bussiness.virus.common.GpsConvertApi;
import com.pcl.healthism.bussiness.virus.poi.Parser;
import com.pcl.healthism.web.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

public class CommonTest extends BaseTest {

    @Autowired
    private Parser parser;

    @Autowired
    private GpsConvertApi gpsConvertApi;

    @Test
    public void testLoad() {
        logger.info("hospital:{}", parser.loadHospitals());
        logger.info("community:{}", parser.loadCommunity());
    }

    @Test
    public void testGps() {
        logger.info("{}", gpsConvertApi.convertTo(Lists.newArrayList(new Pair<Double, Double>(110.0, 22.0),
                new Pair<Double, Double>(111.0, 23.0)), "baidu"));
    }
}
