package com.zhangbin.jackson.core;

import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.zhangbin.jackson.core.annotation.FieldJsonFilter;
import com.zhangbin.jackson.core.annotation.JacksonView;
import com.zhangbin.jackson.core.filter.DefaultPropertyFilter;
import com.zhangbin.jackson.core.filter.ExcludeFieldSerializerFilter;
import com.zhangbin.jackson.core.filter.MaskSerializerFilter;
import com.zhangbin.jackson.result.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.JsonViewResponseBodyAdvice;

import java.util.Objects;
import java.util.stream.Stream;

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
        JacksonView jacksonView = returnType.getMethodAnnotation(JacksonView.class);
        if (Objects.isNull(jacksonView)) {
            super.beforeBodyWriteInternal(bodyContainer, contentType, returnType, request, response);
            return;
        }
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        DefaultPropertyFilter filter = new DefaultPropertyFilter();

        filter.addFilter(new MaskSerializerFilter(jacksonView.mask()), buildExcludeFiler(jacksonView.exclude()));
        filterProvider.addFilter(DefaultPropertyFilter.FILTER_ID, filter);
        bodyContainer.setFilters(filterProvider);
    }

    private ExcludeFieldSerializerFilter buildExcludeFiler(FieldJsonFilter[] filters) {
        ExcludeFieldSerializerFilter excludeFieldSerializerFilter = new ExcludeFieldSerializerFilter();
        Stream.of(filters).forEach(filter -> excludeFieldSerializerFilter.addExcludeField(filter.clazz(), filter.props()));
        return excludeFieldSerializerFilter;
    }

}
