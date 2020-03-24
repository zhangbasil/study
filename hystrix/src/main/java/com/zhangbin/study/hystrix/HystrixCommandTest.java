package com.zhangbin.study.hystrix;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class HystrixCommandTest {

    public static void main(String[] args) {

        String exe = new HelloWorldHystrixCommand("zhangbin").execute();

//        Future<String> future = new HelloWorldHystrixCommand("zhangbin").queue();

        System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());
        System.out.println("exe = " + exe);

    }
}
