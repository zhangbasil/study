package com.zhangbin.jackson.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@RestController
public class JacksonController {



    @GetMapping("/index")
    public User user() {
        return User.builder().userId(10000L)
                .userName("张三丰")
                .password("1828282812")
                .mobile("17666666666")
                .createTime(LocalDateTime.now())
                .build();
    }




}
