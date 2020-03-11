package com.zhangbin.tool.excel.annotatoin;

import java.lang.annotation.*;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Style {

    int width() default 15;

}
