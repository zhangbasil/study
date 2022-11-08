package com.zhangbin.tool.thread.lock;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class Demo {
    public static void main(String[] args) {
        int MAX_CAP      = 0x7fff;        // max #workers - 1

        int num = Runtime.getRuntime().availableProcessors();

        System.out.println("num = " + num);
    }
}
