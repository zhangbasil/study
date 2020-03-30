package com.zhangbin.jackson.controller;

import com.zhangbin.jackson.core.annotation.Mask;
import com.zhangbin.jackson.core.annotation.MaskField;
import com.zhangbin.jackson.core.annotation.MaskJsonFilter;
import com.zhangbin.jackson.core.annotation.MaskJsonView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@RestController
public class JacksonController {


    @GetMapping("/index")
    @MaskJsonView(mask = {
            @MaskJsonFilter(clazz = User.class, props = @MaskField(name = "mobile", pattern = @Mask(left = 3, right = 4))),
            @MaskJsonFilter(clazz = User.class, props = @MaskField(name = "userName", pattern = @Mask(left = 1, right = 0, repeat = 2)))
    })
    public User user() {

        return User.builder().userId(10000L)
                .userName("张三丰")
                .password("1828282812")
                .mobile("17666666666")
                .build();
    }


}
