package com.zhangbin.jackson.core.filter;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 * jackson filter 每次只能设置一个filter  DefaultPropertyFilter 集成了 掩码和属性过滤的filter
 */
@JsonFilter(DefaultPropertyFilter.FILTER_ID)
public class DefaultPropertyFilter extends SimpleBeanPropertyFilter {

    public static final String FILTER_ID = "DefaultPropertyFilter";

    private final List<SerializeFilter> filters = new ArrayList<>();

    public DefaultPropertyFilter() {
        addFilter(new ExcludeFieldSerializerFilter());
    }

    @Override
    public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider provider, PropertyWriter writer) throws Exception {

        for (SerializeFilter filter : filters) {
            filter.serializeField(pojo, writer, jgen, provider);
        }

    }

    public void addFilter(SerializeFilter... filters) {
        this.filters.addAll(Arrays.asList(filters));
    }

}
