package com.zhangbin.jackson.controller;

import com.zhangbin.jackson.core.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public User user() {
        User share = User.builder().userId(9999L).userName("分享人").password("1000000").mobile("188888776").build();
        User user = User.builder()
                .userId(10000L)
                .userName("张三丰")
                .password("666888")
                .mobile("17612346666")
                .share(share)
                .build();

        return user;
    }


}
