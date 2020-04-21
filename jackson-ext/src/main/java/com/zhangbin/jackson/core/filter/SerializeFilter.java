package com.zhangbin.jackson.core.filter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.PropertyWriter;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public interface SerializeFilter {


    default boolean include(Object pojo, PropertyWriter writer) {
        return true;
    }

    default void process(Object pojo, PropertyWriter writer) throws Exception {

    }

    default void serializeField(Object pojo, PropertyWriter writer, JsonGenerator jgen, SerializerProvider provider) throws Exception {
        this.process(pojo, writer);

        if (include(pojo, writer)) {
            writer.serializeAsField(pojo, jgen, provider);
        }
    }


}
