package com.zhangbin.concurrency.lock;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class B {

    public static void main(String[] args) {
        new A();
        String str = A.STR;
        System.out.println("str = " + str);

    }
}
