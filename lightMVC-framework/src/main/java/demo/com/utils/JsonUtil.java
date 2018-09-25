package demo.com.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JsonUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    //从 对象转为 json
    public static <T> String toJson(T obj)
    {
        String json;
        try {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOGGER.error("serialize to json failure! object:{}",obj,e);
            throw new RuntimeException(e);
        }
        return json;
    }

    //从json 转为 对象
    public static <T> T fromJson(String json, Class<T> type){
        T t;
        try {
            t = OBJECT_MAPPER.readValue(json, type);
        } catch (IOException e) {
            LOGGER.error("mapping to object failure! json:{}",json,e);
            throw new RuntimeException(e);
        }
        return t;
    }

}
