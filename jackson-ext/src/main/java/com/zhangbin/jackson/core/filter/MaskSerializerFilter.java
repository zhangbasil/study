package com.zhangbin.jackson.core.filter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.zhangbin.jackson.core.annotation.Mask;
import com.zhangbin.jackson.core.annotation.MaskField;
import com.zhangbin.jackson.core.annotation.MaskJsonFilter;
import com.zhangbin.jackson.utils.StringUtils;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class MaskSerializerFilter implements SerializeFilter {

    private final MaskJsonFilter[] maskJsonFilters;

    public MaskSerializerFilter(MaskJsonFilter[] maskJsonFilters) {
        this.maskJsonFilters = maskJsonFilters;
    }


    @Override
    public void process(Object pojo, PropertyWriter writer) throws Exception {
        if (Objects.isNull(maskJsonFilters)) {
            return;
        }
        Class<?> clazz = pojo.getClass();
        for (MaskJsonFilter maskJsonFilter : maskJsonFilters) {
            if (clazz != maskJsonFilter.clazz()) {
                continue;
            }
            String fieldName = writer.getName();
            AnnotatedMember member = writer.getMember();
            for (MaskField prop : maskJsonFilter.props()) {
                if (!prop.name().equals(fieldName)) {
                    continue;
                }
                Mask pattern = prop.pattern();
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(pojo, doMask(pattern, member.getValue(pojo).toString()));
            }
        }
    }



    private String doMask(Mask maskField, String value) {
        String left = StringUtils.left(value, maskField.left());
        String placeholder = StringUtils.repeat(maskField.placeholder(), maskField.repeat());
        String right = StringUtils.right(value, maskField.right());
        return left + placeholder + right;
    }
}
