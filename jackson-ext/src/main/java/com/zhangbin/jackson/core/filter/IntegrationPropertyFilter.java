package com.zhangbin.jackson.core.filter;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.zhangbin.jackson.core.annotation.JacksonView;
import com.zhangbin.jackson.core.annotation.Mask;
import com.zhangbin.jackson.core.annotation.MaskField;
import com.zhangbin.jackson.core.annotation.MaskJsonFilter;
import com.zhangbin.jackson.utils.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 * jackson filter 每次只能设置一个 IntegrationPropertyFilter 集成了 掩码和属性过滤的filter
 */
@JsonFilter(IntegrationPropertyFilter.FILTER_ID)
public class IntegrationPropertyFilter extends SimpleBeanPropertyFilter {

    public static final String FILTER_ID = "IntegrationPropertyFilter";

    private final JacksonView jacksonView;

    public IntegrationPropertyFilter(JacksonView jacksonView) {
        this.jacksonView = jacksonView;
    }


    @Override
    public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider provider, PropertyWriter writer) throws Exception {

        this.handleMaskJsonFilter(pojo, writer);

        super.serializeAsField(pojo, jgen, provider, writer);
    }

    private void handleMaskJsonFilter(Object pojo, PropertyWriter writer) throws NoSuchFieldException, IllegalAccessException {
        MaskJsonFilter[] maskJsonFilters = jacksonView.mask();
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


    @Override
    protected boolean include(PropertyWriter writer) {
        Class<?> declaringClass = writer.getMember().getDeclaringClass();
        return Stream.of(jacksonView.exclude()).allMatch(item -> item.clazz() != declaringClass || !Arrays.asList(item.props()).contains(writer.getName()));
    }


    private String doMask(Mask maskField, String value) {
        String left = StringUtils.left(value, maskField.left());
        String placeholder = StringUtils.repeat(maskField.placeholder(), maskField.repeat());
        String right = StringUtils.right(value, maskField.right());
        return left + placeholder + right;
    }

}
