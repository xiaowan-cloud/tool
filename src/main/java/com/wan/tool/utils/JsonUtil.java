package com.wan.tool.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author wan
 * @version 1.0.0
 * @Description ObjectMapper的方法封装
 * @createTime 2021年08月05日 14:17:08
 */
@Slf4j
public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * json转对象(这个只可用于非嵌套对象的转换)
     *
     * @param json
     * @param clazz
     * @return
     */
    //<T> T 意思返回值也是泛型 T 只能返回传入的T类型
    public static <T> T json2Object(String json, Class<T> clazz) {
        try {
            return (String.class).equals(clazz) ? (T) json : objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 功能描述：
     * -- json转对象(通用)
     *
     * @param : [json, typeReference]
     * @return : T
     * @author : wan
     * @date : 2022/1/13 15:25
     */
    public static <T> T json2Object(String str, TypeReference typeReference) {
        if (StringUtils.isEmpty(str) || typeReference == null) {
            return null;
        }
        try {
            return (T) (typeReference.getType().equals(String.class) ? str : objectMapper.readValue(str, typeReference));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对象转json
     *
     * @param obj
     * @return
     */
    public static <T> String object2Json(T obj) {
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

}
