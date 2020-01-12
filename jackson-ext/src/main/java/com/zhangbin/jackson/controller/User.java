package com.zhangbin.jackson.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
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

        ObjectMapper mapper = new ObjectMapper();
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("userFilter", SimpleBeanPropertyFilter.filterOutAllExcept("userId", "userName"));
        mapper.setFilterProvider(filterProvider);
        String userStr = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(user);
        System.out.println("userStr = " + userStr);
    }

}
