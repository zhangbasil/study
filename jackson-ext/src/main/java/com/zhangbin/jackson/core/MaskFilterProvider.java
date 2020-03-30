package com.zhangbin.jackson.core;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import java.util.Objects;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class MaskFilterProvider extends SimpleFilterProvider implements PropertyFilter {

    public MaskFilterProvider(PropertyFilter propertyFilter) {
        _defaultFilter = Objects.requireNonNull(propertyFilter);
        addFilter(MaskPropertyFilter.FILTER_ID, this);
    }

    @Override
    public void serializeAsField(Object pojo, JsonGenerator gen, SerializerProvider prov, PropertyWriter writer) throws Exception {
        _defaultFilter.serializeAsField(pojo, gen, prov, writer);
    }

    @Override
    public void serializeAsElement(Object elementValue, JsonGenerator gen, SerializerProvider prov, PropertyWriter writer) throws Exception {
        _defaultFilter.serializeAsElement(elementValue, gen, prov, writer);
    }

    @Override
    public void depositSchemaProperty(PropertyWriter writer, ObjectNode propertiesNode, SerializerProvider provider) throws JsonMappingException {
        _defaultFilter.depositSchemaProperty(writer, propertiesNode, provider);
    }

    @Override
    public void depositSchemaProperty(PropertyWriter writer, JsonObjectFormatVisitor objectVisitor, SerializerProvider provider) throws JsonMappingException {
        _defaultFilter.depositSchemaProperty(writer, objectVisitor, provider);
    }
}
