package com.zhangbin.threadlocal.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhangbin
 * @Type RequestLine
 * @Desc
 * @date 2019-03-04
 * @Version V1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestLine {

    String path() default "";

    RequestMethod method() default RequestMethod.GET;


    enum RequestMethod {
        GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE

    }
}
