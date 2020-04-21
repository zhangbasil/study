package com.zhangbin.jackson.configurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.zhangbin.jackson.core.DefaultJacksonAnnotationIntrospector;
import com.zhangbin.jackson.core.filter.DefaultPropertyFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@Configuration
public class JacksonWebMvcConfigurer implements WebMvcConfigurer {

    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Bean
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper objectMapper() {
        return Jackson2ObjectMapperBuilder
                .json()
                .simpleDateFormat(DATE_PATTERN)
                .serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_PATTERN)))
                .annotationIntrospector(new DefaultJacksonAnnotationIntrospector())
                .filters(new SimpleFilterProvider().setFailOnUnknownId(false).addFilter(DefaultPropertyFilter.FILTER_ID, new DefaultPropertyFilter()))
                .mixIn(Object.class, DefaultPropertyFilter.class)
                .build();
    }
}
