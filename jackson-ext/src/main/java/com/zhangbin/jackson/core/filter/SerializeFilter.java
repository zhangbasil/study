package com.zhangbin.jackson.core.filter;

import com.fasterxml.jackson.databind.ser.PropertyWriter;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public interface SerializeFilter {

    default void process(Object pojo, PropertyWriter writer) throws Exception {

    }
}
