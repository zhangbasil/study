package com.zhangbin.jackson.filter;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.zhangbin.jackson.annotation.Mask;
import com.zhangbin.jackson.utils.StringUtils;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@JsonFilter("maskFilter")
public class MaskPropertyFilter implements PropertyFilter {

    @Override
    public void serializeAsField(Object pojo, JsonGenerator gen, SerializerProvider prov, PropertyWriter writer) throws Exception {
        Mask mask = writer.getAnnotation(Mask.class);
        if (Objects.nonNull(mask)) {
            AnnotatedMember member = writer.getMember();
            Field field = pojo.getClass().getDeclaredField(writer.getFullName().getSimpleName());
            field.setAccessible(true);
            field.set(pojo, doMask(mask, member.getValue(pojo).toString()));
        }
        writer.serializeAsField(pojo, gen, prov);
    }

    @Override
    public void serializeAsElement(Object elementValue, JsonGenerator gen, SerializerProvider prov, PropertyWriter writer) throws Exception {

    }

    @Override
    public void depositSchemaProperty(PropertyWriter writer, ObjectNode propertiesNode, SerializerProvider provider) throws JsonMappingException {

    }

    @Override
    public void depositSchemaProperty(PropertyWriter writer, JsonObjectFormatVisitor objectVisitor, SerializerProvider provider) throws JsonMappingException {

    }

    private String doMask(Mask maskField, String value) {
        String left = StringUtils.left(value, maskField.left());
        String placeholder = StringUtils.repeat(maskField.placeholder(), maskField.repeat());
        String right = StringUtils.right(value, maskField.right());
        return left + placeholder + right;
    }
}
