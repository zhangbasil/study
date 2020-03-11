package com.zhangbin.jackson.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.zhangbin.jackson.core.DefaultJacksonAnnotationIntrospector;
import com.zhangbin.jackson.core.annotation.Mask;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@Getter
@Setter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    Long userId;
    String userName;
    @JsonIgnore
    String password;
    @Mask(left = 3, right = 4)
    String mobile;

    public static void main(String[] args) throws JsonProcessingException {
        User user = User.builder()
                .userId(10000L)
                .userName("张三丰")
                .password("666888")
                .mobile("17612346666")
                .build();
        JsonMapper jsonMapper = JsonMapper.builder().build();
        jsonMapper.setAnnotationIntrospector(new DefaultJacksonAnnotationIntrospector());
        String userStr = jsonMapper.writer().writeValueAsString(user);
        System.out.println("userStr = " + userStr);
    }

}
