package com.zhangbin.study.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class HelloWorldHystrixCommand extends HystrixCommand<String> {

    private final String name;

    protected HelloWorldHystrixCommand(String name) {
        super(HystrixCommandGroupKey.Factory.asKey(""));
        this.name = name;
    }


    @Override
    protected String run() throws Exception {
        System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());
        return "Hello " + name + " !";
    }

}
