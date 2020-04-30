package com.zhangbin.jackson.controller;

import com.zhangbin.jackson.core.annotation.*;
import com.zhangbin.jackson.pojo.User;
import com.zhangbin.jackson.result.Result;
import com.zhangbin.jackson.result.Results;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@RestController
public class JacksonController {


    @GetMapping("/index")
    @JacksonView(
            mask = {
                    @MaskJsonFilter(clazz = User.class, props = {
                            @MaskField(name = "mobile", pattern = @Mask(left = 3, right = 4)),
                            @MaskField(name = "userName", pattern = @Mask(right = 0, repeat = 2))
                    })},
            exclude = {
                    @FieldJsonFilter(clazz = User.class, props = {"userId", "password"})
            }
    )
    public Result<User> user() {
        User share = User.builder().userId(9999L).userName("分享人").password("1000000").mobile("188888776").build();
        User user = User.builder()
                .userId(10000L)
                .userName("张三丰")
                .password("666888")
                .mobile("17612346666")
                .share(share)
                .createDate(new Date())
                .updateDate(LocalDate.now())
                .modifyTime(LocalDateTime.now())
                .build();
        return Results.success(user);
    }


    @GetMapping("/invalid")
    public Result<User> invalid() {
        return Results.invalid();
    }


    @GetMapping("/user")
    public User user1() {
        User share = User.builder().userId(9999L).userName("分享人").password("1000000").mobile("188888776").build();
        return User.builder()
                .userId(10000L)
                .userName("张三丰")
                .password("666888")
                .mobile("17612346666")
                .share(share)
                .createDate(new Date())
                .updateDate(LocalDate.now())
                .modifyTime(LocalDateTime.now())
                .build();
    }

}
