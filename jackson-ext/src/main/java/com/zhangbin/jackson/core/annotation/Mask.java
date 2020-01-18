package com.zhangbin.jackson.core.annotation;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhangbin.jackson.core.MaskSerializer;

import java.lang.annotation.*;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Inherited
@JsonSerialize(using = MaskSerializer.class)
public @interface Mask {


    /**
     * 掩码占位符
     *
     * @return
     */
    String placeholder() default "*";

    /**
     * placeholder 重复的次数
     *
     * @return
     */
    int repeat() default 4;

    /**
     * 保留掩码起始位置；例如：手机号 -> 1*********
     *
     * @return
     */
    int left() default 1;

    /**
     * 保留掩码结束位置；例如：手机号 -> 1*********9
     *
     * @return
     */
    int right() default 1;

}
