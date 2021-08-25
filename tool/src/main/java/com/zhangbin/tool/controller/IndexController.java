package com.zhangbin.tool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@Controller
public class IndexController {


    @GetMapping("/")
    public String hello() {
        return "/index";
    }


}
