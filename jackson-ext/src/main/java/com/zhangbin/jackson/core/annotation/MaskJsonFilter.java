package com.zhangbin.jackson.core.annotation;

import java.lang.annotation.*;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MaskJsonFilter {

    Class<?> clazz();

    MaskField[] props();
}
