package com.pcl.healthism.bussiness.common.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;


/***
 * 使用application.yml配置好的统一的jackson objectMapper，保持json处理行为的应用一致性
 * 注意该工具类没法在springboot启动前使用，因为使用的是springboot的ObjectMapper的单例
 * */
@Component
public class JsonTool {
    private static ObjectMapper mapper;
    private final static Logger LOG = LoggerFactory.getLogger(JsonTool.class);

    @Autowired
    private void init(ObjectMapper objectMapper) {
        mapper = objectMapper;
    }

    /**
     *  对象序列化为json字符串
     * @throws IllegalStateException 序列化失败会抛出非法状态exception
     */
    public static String serialize(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            LOG.error("object serialize error", e);
            throw new IllegalStateException(e);
        }
    }

    /**
     *
     * @throws IllegalStateException 反序列化失败会抛出非法状态exception
     */
    public static <T> T parse(String jsonValue, Class<T> tClass) {
        try {
            return mapper.readValue(jsonValue, tClass);
        } catch (IOException e) {
            LOG.error("object parse error, source json string={}", jsonValue, e);
            throw new IllegalStateException(e);
        }
    }

    /**
     * @throws IllegalStateException 反序列化失败会抛出非法状态exception
     */
    public static <T> T parse(String jsonValue, TypeReference typeReference) {
        try {
            return mapper.readValue(jsonValue, typeReference);
        } catch (IOException e) {
            LOG.error("object parse error", e);
            throw new IllegalStateException(e);
        }
    }
}
