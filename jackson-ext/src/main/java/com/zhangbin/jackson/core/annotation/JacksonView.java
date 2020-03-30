package com.zhangbin.jackson.core.annotation;


import java.lang.annotation.*;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JacksonView {

    MaskJsonFilter[] mask() default {};

    FieldJsonFilter[] exclude() default {};

}
