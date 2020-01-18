package com.zhangbin.jackson.core.annotation;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhangbin.jackson.core.OtherSerializer;

import java.lang.annotation.*;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Inherited
@JsonSerialize(using = OtherSerializer.class)
public @interface Other {


}
