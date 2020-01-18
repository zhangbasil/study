package com.zhangbin.jackson.core;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.zhangbin.jackson.core.annotation.Mask;
import com.zhangbin.jackson.utils.StringUtils;

import java.io.IOException;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class MaskSerializer extends StdSerializer<String> implements ContextualSerializer {

    private Mask mask;

    public MaskSerializer() {
        super(String.class);
    }

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeObject(doMask(value));
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        mask = property.getAnnotation(Mask.class);
        return this;
    }

    private String doMask(String value) {
        String left = StringUtils.left(value, mask.left());
        String placeholder = StringUtils.repeat(mask.placeholder(), mask.repeat());
        String right = StringUtils.right(value, mask.right());
        return left + placeholder + right;
    }
}
