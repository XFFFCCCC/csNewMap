package com.pcl.healthism.web.service;

import com.pcl.healthism.bussiness.virus.data.GlobalDataService;
import com.pcl.healthism.bussiness.virus.data.VirusData;
import com.pcl.healthism.web.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class VirusGatewayTest extends BaseTest {

    @Autowired
    private GlobalDataService dataService;

    @Test
    public void testQuery() {
        for (int i=0 ;i < 100; i++) {
            VirusData data = dataService.get();
            logger.info("{}", data);
        }
    }
}
