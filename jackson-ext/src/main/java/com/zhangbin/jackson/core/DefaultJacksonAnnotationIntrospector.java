package com.zhangbin.jackson.core;

import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.zhangbin.jackson.core.annotation.Mask;
import com.zhangbin.jackson.core.annotation.Other;

import java.util.stream.Stream;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class DefaultJacksonAnnotationIntrospector extends JacksonAnnotationIntrospector {

    public DefaultJacksonAnnotationIntrospector() {
        Stream.of(Mask.class, Other.class).forEach(clazz -> _annotationsInside.put(clazz, true));
    }
}
