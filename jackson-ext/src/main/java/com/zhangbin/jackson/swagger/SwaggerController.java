package com.zhangbin.jackson.swagger;

import com.zhangbin.jackson.pojo.User;
import com.zhangbin.jackson.result.Result;
import com.zhangbin.jackson.result.Results;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@RestController
public class SwaggerController {



    @GetMapping("/swagger/user")
    public Result<User> user() {
        User share = User.builder().userId(9999L).userName("分享人").password("1000000").mobile("188888776").build();
        User user = User.builder()
                .userId(10000L)
                .userName("张三丰")
                .password("666888")
                .mobile("17612346666")
                .share(share)
                .build();
        return Results.success(user);
    }


}
