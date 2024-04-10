package com.pcl.healthism.bussiness.common.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Future;

public class FutureTool {

    private static Logger logger = LoggerFactory.getLogger(FutureTool.class);

    /**
     * 同步获取future结果，并且用默认值替代异常返回
     */
    public static <T> T get(Future<T> future, T defaultValue) {
        try {
            return future.get();
        } catch (Exception e) {
            logger.error("future get with error, default value is {}", defaultValue, e);
        }
        return defaultValue;
    }
}
