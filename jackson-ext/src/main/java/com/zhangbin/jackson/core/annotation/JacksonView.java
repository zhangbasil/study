package com.zhangbin.jackson.core.annotation;


import java.lang.annotation.*;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JacksonView {

    /**
     * 掩码的过滤器
     *
     * @return
     */
    MaskJsonFilter[] mask() default {};

    /**
     * 排除字段过滤器
     *
     * @return
     */
    FieldJsonFilter[] exclude() default {};

}
