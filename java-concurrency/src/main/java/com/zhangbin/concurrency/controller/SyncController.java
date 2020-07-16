package com.zhangbin.concurrency.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.CompletableFuture;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@RestController
public class SyncController {


    @GetMapping("/async")
    public Object sync(HttpServletResponse response) {
        try {
            CompletableFuture.supplyAsync(this::hello)
                    .thenAccept(t -> System.out.println("t = " + t));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis());
        return "success";
    }

    private String hello() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis());
        return "hello";
    }

}
