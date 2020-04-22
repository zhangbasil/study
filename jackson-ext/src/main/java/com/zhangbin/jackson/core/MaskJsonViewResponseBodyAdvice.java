package com.zhangbin.jackson.core;

import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.zhangbin.jackson.core.annotation.FieldJsonFilter;
import com.zhangbin.jackson.core.annotation.JacksonView;
import com.zhangbin.jackson.core.filter.DefaultPropertyFilter;
import com.zhangbin.jackson.core.filter.MaskSerializerFilter;
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
        filterProvider.addFilter(DefaultPropertyFilter.FILTER_ID, buildFiler(jacksonView));
        bodyContainer.setFilters(filterProvider);
    }

    private DefaultPropertyFilter buildFiler(JacksonView jacksonView) {
        DefaultPropertyFilter filter = new DefaultPropertyFilter();
        filter.addFilters(new MaskSerializerFilter(jacksonView.mask()));
        Stream.of(jacksonView.exclude()).forEach(item -> filter.addExcludeField(item.clazz(), item.props()));
        return filter;
    }

}
