package com.zhangbin.jackson.configurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.zhangbin.jackson.core.DefaultJackson2HttpMessageConverter;
import com.zhangbin.jackson.core.DefaultJacksonAnnotationIntrospector;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@Configuration
public class JacksonWebMvcConfigurer implements WebMvcConfigurer {

    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

//    @Bean
//    @ConditionalOnMissingBean(ObjectMapper.class)
//    public ObjectMapper objectMapper() {
//        return Jackson2ObjectMapperBuilder
//                .json()
//                .simpleDateFormat(DATE_PATTERN)
//                .serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_PATTERN)))
//                .annotationIntrospector(new DefaultJacksonAnnotationIntrospector())
//                .build();
//    }

    @Bean
    @ConditionalOnMissingBean(value = MappingJackson2HttpMessageConverter.class,
            ignoredType = {
                    "org.springframework.hateoas.server.mvc.TypeConstrainedMappingJackson2HttpMessageConverter",
                    "org.springframework.data.rest.webmvc.alps.AlpsJsonHttpMessageConverter"})
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder
                .json()
                .simpleDateFormat(DATE_PATTERN)
                .serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_PATTERN)))
                .annotationIntrospector(new DefaultJacksonAnnotationIntrospector())
                .build();
        return new DefaultJackson2HttpMessageConverter(objectMapper);
    }
}
