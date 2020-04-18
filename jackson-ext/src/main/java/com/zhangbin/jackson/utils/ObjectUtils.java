package com.zhangbin.jackson.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public final class ObjectUtils {

    private static final JsonMapper jsonMapper = JsonMapper.builder()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .addModules(new Jdk8Module(), new JavaTimeModule())
            .build();

    public static <T> T copyBean(Object source, Class<T> target) {
        return readValue(writeAsString(source), target);
    }

    public static <T> List<T> copyBeans(List<Object> source, Class<T> target) {
        return source.stream().map(item -> copyBean(item, target)).collect(Collectors.toList());
    }

    public static String writeAsString(Object content) {
        try {
            return jsonMapper.writer().writeValueAsString(content);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("对象 [ " + content + " ] 转换Json字符串异常：" + e.getMessage(), e);
        }
    }

    public static <T> T readValue(String content, Class<T> target) {
        try {
            return jsonMapper.readValue(content, target);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json字符串 [ " + content + " ] 转换对象 [ " + target + " ] 异常：" + e.getMessage(), e);
        }
    }

}
