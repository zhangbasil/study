package com.zhangbin.jackson.controller;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.zhangbin.jackson.annotation.Mask;
import com.zhangbin.jackson.filter.DemoPropertyFilter;
import com.zhangbin.jackson.filter.MaskPropertyFilter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createTime;


    public static void main(String[] args) throws JsonProcessingException {

        User user = User.builder().userId(10000L)
                .userName("张三丰")
                .password("1828282812")
                .mobile("17666666666")
                .createTime(LocalDateTime.now())
                .build();


        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("DemoPropertyFilter", new DemoPropertyFilter())
                .addFilter("maskFilter", new MaskPropertyFilter());

        JsonMapper jsonMapper = JsonMapper.builder().build();
        jsonMapper.addMixIn(User.class, DemoPropertyFilter.class)
                .addMixIn(User.class, MaskPropertyFilter.class);
        String userStr = jsonMapper.setFilterProvider(filterProvider).writer().withDefaultPrettyPrinter()
                .writeValueAsString(user);


        System.out.println("userStr = " + userStr);
    }

}
