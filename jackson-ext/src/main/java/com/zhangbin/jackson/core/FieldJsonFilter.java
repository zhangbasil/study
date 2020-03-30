package com.zhangbin.jackson.core;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.zhangbin.jackson.core.annotation.Mask;
import com.zhangbin.jackson.core.annotation.MaskField;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@JsonFilter(FieldJsonFilter.FILTER_ID)
public class FieldJsonFilter extends SimpleBeanPropertyFilter {

    public static final String FILTER_ID = "fieldJsonFilter";


    private Map<Class<?>, String[]> fieldMap;

    public FieldJsonFilter(Map<Class<?>, String[]> fieldMap) {
        this.fieldMap = Objects.requireNonNull(fieldMap);
    }

    @Override
    public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider provider, PropertyWriter writer) throws Exception {
        Class<?> clazz = pojo.getClass();
        String fieldName = writer.getFullName().getSimpleName();
        String[] props = fieldMap.get(clazz);
        for (String prop : props) {
            if (fieldName.equals(prop)) {

            }
        }
        writer.serializeAsField(pojo, jgen, provider);
    }
}
