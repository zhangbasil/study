package com.zhangbin.threadlocal.controller;

import com.zhangbin.threadlocal.demo.DemoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhangbin
 * @Type IndexController
 * @Desc
 * @date 2019-03-01
 * @Version V1.0
 */
@RestController
public class IndexController {

    @Resource
    private DemoService demoService;

    @GetMapping("/index")
    public Object index() {
        return "hello world !";
    }

    @GetMapping("/demo")
    public Object demo() {
        return demoService.sendReq("张斌");
    }

}
