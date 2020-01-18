package com.zhangbin.jackson.core;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.zhangbin.jackson.core.annotation.Mask;
import com.zhangbin.jackson.core.annotation.MaskField;
import com.zhangbin.jackson.utils.StringUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@JsonFilter("maskPropertyFilter")
public class MaskPropertyFilter extends DefaultPropertyFilter {

    private Map<Class<?>, MaskField[]> maskFieldMap;

    public MaskPropertyFilter(Map<Class<?>, MaskField[]> maskFieldMap) {
        this.maskFieldMap = maskFieldMap;
    }


    @Override
    public void serializeAsField(Object pojo, JsonGenerator gen, SerializerProvider prov, PropertyWriter writer) throws Exception {
        Class<?> clazz = pojo.getClass();
        String fieldName = writer.getFullName().getSimpleName();
        MaskField[] maskFields = maskFieldMap.get(clazz);
        if (!CollectionUtils.isEmpty(maskFieldMap)) {
            for (MaskField maskField : maskFields) {
                String name = maskField.name();
                if (fieldName.equals(name)) {
                    AnnotatedMember member = writer.getMember();
                    Mask pattern = maskField.pattern();
                    Field field = clazz.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    field.set(pojo, doMask(pattern, member.getValue(pojo).toString()));
                }
            }

        }
        writer.serializeAsField(pojo, gen, prov);
    }


    private String doMask(Mask maskField, String value) {
        String left = StringUtils.left(value, maskField.left());
        String placeholder = StringUtils.repeat(maskField.placeholder(), maskField.repeat());
        String right = StringUtils.right(value, maskField.right());
        return left + placeholder + right;
    }
}
