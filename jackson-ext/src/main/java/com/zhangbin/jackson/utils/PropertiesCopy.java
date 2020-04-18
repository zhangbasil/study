package com.zhangbin.jackson.utils;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public final class PropertiesCopy {

    public static <T> T copyBean(Object source, Class<T> target) {
        try {
            JsonMapper jsonMapper = JsonMapper.builder().build();
            jsonMapper.registerModule(new Jdk8Module());
            jsonMapper.registerModule(new JavaTimeModule());
            jsonMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return jsonMapper.readValue(jsonMapper.writer().writeValueAsString(source), target);
        } catch (Exception e) {
            throw new RuntimeException("对象转换异常：" + e.getMessage(), e);
        }
    }

    public static <T> List<T> copyBeans(List<Object> source, Class<T> target) {
        return source.stream().map(item -> copyBean(item, target)).collect(Collectors.toList());
    }

}
