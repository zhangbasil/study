package com.zhangbin.jackson.core.filter;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.zhangbin.jackson.result.Result;

import java.util.*;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 * jackson filter 每次只能设置一个filter  DefaultPropertyFilter 集成了 掩码和属性过滤的filter
 */
@JsonFilter(DefaultPropertyFilter.FILTER_ID)
public class DefaultPropertyFilter extends SimpleBeanPropertyFilter {

    public static final String FILTER_ID = "DefaultPropertyFilter";

    private final List<SerializeFilter> filters = new ArrayList<>();

    private final Map<Class<?>, String[]> excludeFields = new HashMap<>();


    public DefaultPropertyFilter() {
        addExcludeField(Result.class, new String[0]);
    }

    @Override
    public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider provider, PropertyWriter writer) throws Exception {
        if (!include(pojo, writer)) {
            return;
        }
        for (SerializeFilter filter : filters) {
            filter.process(pojo, writer);
        }
        super.serializeAsField(pojo, jgen, provider, writer);
    }

    public void addFilters(SerializeFilter... filters) {
        this.filters.addAll(Arrays.asList(filters));
    }

    public void addExcludeField(Class<?> clazz, String[] fields) {
        String[] props = this.excludeFields.get(clazz);
        if (Objects.isNull(props)) {
            this.excludeFields.put(clazz, fields);
            return;
        }
        String[] newProps = Arrays.copyOf(props, props.length + fields.length);
        System.arraycopy(fields, 0, newProps, props.length, fields.length);
        this.excludeFields.put(clazz, newProps);
    }

    private boolean include(Object pojo, PropertyWriter writer) {
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


    private static final String VIOLATION_ITEMS = "violationItems";
    private static final String DATA = "data";

    private boolean includeField4Result(Object pojo, String name) {
        if (pojo instanceof Result) {
            Result<?> result = (Result<?>) pojo;
            // 返回结果成功 则不返回Result 中的 violationItems 属性
            return (!result.isSuccess() || !Objects.equals(VIOLATION_ITEMS, name))
                    && (result.isSuccess() || !Objects.equals(DATA, name));
        }
        return true;
    }

}
