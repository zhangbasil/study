package com.zhangbin.tool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@SpringBootApplication(scanBasePackages = {"com.zhangbin.tool"})
public class ToolApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToolApplication.class, args);
    }
}
