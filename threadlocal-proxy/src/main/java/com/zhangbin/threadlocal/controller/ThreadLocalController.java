package com.zhangbin.threadlocal.controller;

import com.zhangbin.threadlocal.model.ReqContext;
import com.zhangbin.threadlocal.service.ThreadLocalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@Slf4j
@RestController
public class ThreadLocalController {

    @Resource
    private ThreadLocalService threadLocalService;

    @GetMapping("/demo")
    public String demo() {
        String val = ReqContext.get().getVal();
        threadLocalService.hello();
        return val;
    }

}
