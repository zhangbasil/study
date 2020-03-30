package com.zhangbin.jackson.core;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.zhangbin.jackson.core.annotation.FieldJsonFilter;
import com.zhangbin.jackson.core.annotation.JacksonView;
import com.zhangbin.jackson.core.annotation.MaskField;
import com.zhangbin.jackson.core.annotation.MaskJsonFilter;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.JsonViewResponseBodyAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@RestControllerAdvice
public class MaskJsonViewResponseBodyAdvice extends JsonViewResponseBodyAdvice {


    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return AbstractJackson2HttpMessageConverter.class.isAssignableFrom(converterType)
                && returnType.hasMethodAnnotation(JacksonView.class);
    }

    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType, MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {
        JacksonView annotation = returnType.getMethodAnnotation(JacksonView.class);
        if (Objects.isNull(annotation)) {
            super.beforeBodyWriteInternal(bodyContainer, contentType, returnType, request, response);
            return;
        }
        bodyContainer.setFilters(buildMaskFilterProvider(annotation.mask()));

        FieldJsonFilter[] fieldJsonFilters = annotation.exclude();

        buildFieldFilterProvider(fieldJsonFilters);

    }

    private MaskFilterProvider buildMaskFilterProvider(MaskJsonFilter[] maskJsonFilters) {
        Map<Class<?>, MaskField[]> maskFieldMap = new HashMap<>();
        for (MaskJsonFilter maskJsonFilter : maskJsonFilters) {
            Class<?> clazz = maskJsonFilter.clazz();
            MaskField[] props = maskJsonFilter.props();
            maskFieldMap.put(clazz, props);
        }
        MaskPropertyFilter propertyFilter = new MaskPropertyFilter(maskFieldMap);
        return new MaskFilterProvider(propertyFilter);
    }

    private SimpleBeanPropertyFilter buildFieldFilterProvider(FieldJsonFilter[] fieldJsonFilters) {

        for (FieldJsonFilter fieldJsonFilter : fieldJsonFilters) {
            Class<?> clazz = fieldJsonFilter.clazz();
            String[] props = fieldJsonFilter.props();
        }

//        com.zhangbin.jackson.core.FieldJsonFilter fieldJsonFilter = new com.zhangbin.jackson.core.FieldJsonFilter();
        return SimpleBeanPropertyFilter.filterOutAllExcept();
    }


}
