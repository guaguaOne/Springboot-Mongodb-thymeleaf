package com.tumei.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tumei.io.protocol.BaseProtocol;

import java.io.IOException;

/**
 * Created by Administrator on 2016/11/24 0024.
 */
public class JsonUtil {
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * 将协议序列化成字节数组
     * @param protocol
     * @return
     */
    public static byte[] Marshal(BaseProtocol protocol) throws JsonProcessingException {
        return mapper.writeValueAsBytes(protocol);
    }

    public static BaseProtocol Unmarshal(byte[] data) throws IOException {
        return mapper.readValue(data, BaseProtocol.class);
    }
}
