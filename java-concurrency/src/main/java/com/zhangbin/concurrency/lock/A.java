package com.zhangbin.concurrency.lock;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class A {

    public static final String STR = "abc";

    public A() {
        System.out.println(" ======> 构造 ");
    }

    static {
        System.out.println(" ======> 静态 ");
    }
}
