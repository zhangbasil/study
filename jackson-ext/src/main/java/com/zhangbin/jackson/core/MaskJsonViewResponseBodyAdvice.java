package com.zhangbin.jackson.core;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.zhangbin.jackson.core.annotation.Mask;
import com.zhangbin.jackson.core.annotation.MaskField;
import com.zhangbin.jackson.core.annotation.MaskJsonFilter;
import com.zhangbin.jackson.core.annotation.MaskJsonView;
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
                && returnType.hasMethodAnnotation(MaskJsonView.class);
    }

    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType, MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {
        MaskJsonView annotation = returnType.getMethodAnnotation(MaskJsonView.class);
        if (Objects.isNull(annotation)) {
            super.beforeBodyWriteInternal(bodyContainer, contentType, returnType, request, response);
            return;
        }
        Map<Class<?>, MaskField[]> maskFieldMap = new HashMap<>();
        MaskJsonFilter[] maskFastJsonFilters = annotation.mask();
        for (MaskJsonFilter maskJsonFilter : maskFastJsonFilters) {
            Class<?> clazz = maskJsonFilter.clazz();
            MaskField[] props = maskJsonFilter.props();
            maskFieldMap.put(clazz, props);
        }

        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        MaskPropertyFilter filter = new MaskPropertyFilter(maskFieldMap);
        filterProvider.setDefaultFilter(filter);
        filterProvider.addFilter("maskPropertyFilter", filter);
        bodyContainer.setFilters(filterProvider);
    }


}
