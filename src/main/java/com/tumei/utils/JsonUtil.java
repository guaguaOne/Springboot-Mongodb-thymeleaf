package com.tumei.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by Administrator on 2016/11/24 0024.
 */
public class JsonUtil {
    private static ObjectMapper mapper = new ObjectMapper();

    /***
     * 初始化配置
     */
    public static void init() {
//        mapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.ANY);
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }




    public static <T> T Unmarshal(byte[] data, Class<T> cls) throws IOException {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(data, cls);
    }

    public static String Serialize(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

}
