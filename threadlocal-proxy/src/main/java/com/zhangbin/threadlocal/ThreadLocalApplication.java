package com.zhangbin.threadlocal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.zhangbin.threadlocal"})
public class ThreadLocalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThreadLocalApplication.class, args);
	}

}
