package com.zhangbin.jackson.core.filter;

import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.zhangbin.jackson.result.Result;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class ExcludeFieldSerializerFilter implements SerializeFilter {

    private static final String VIOLATION_ITEMS = "violationItems";

    private static final String DATA = "data";


    private final Map<Class<?>, String[]> excludeFields = new HashMap<>();

    public ExcludeFieldSerializerFilter() {
        addExcludeField(Result.class, new String[0]);
    }

    public ExcludeFieldSerializerFilter addExcludeField(Class<?> clazz, String[] fields) {
        this.excludeFields.put(clazz, fields);
        return this;
    }

    @Override
    public boolean include(Object pojo, PropertyWriter writer) {
        String name = writer.getName();
        Class<?> declaringClass = writer.getMember().getDeclaringClass();
        String[] props = null;
        for (Class<?> key : excludeFields.keySet()) {
            if (key.isAssignableFrom(declaringClass)) {
                props = excludeFields.get(key);
                break;
            }
        }
        if (Objects.isNull(props)) {
            return true;
        }
        return includeField4Result(pojo, name) && !Arrays.asList(props).contains(name);
    }

    private boolean includeField4Result(Object pojo, String name) {
        if (pojo instanceof Result) {
            Result<?> result = (Result<?>) pojo;
            // 返回结果成功或者错误 则不返回Result 中的 violationItems 属性
            if ((result.isSuccess() || result.isError()) && Objects.equals(VIOLATION_ITEMS, name)) {
                return false;
            }
            // 不成功 则不返回 data 属性
            return result.isSuccess() || !Objects.equals(DATA, name);
        }
        return true;
    }

}
