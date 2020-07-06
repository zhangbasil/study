package com.zhangbin.tool.thread;

import java.io.IOException;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class ThreadPoolTest {

    public static void main(String[] args) throws IOException, InterruptedException {

        Worker worker = new Worker();

        new Thread(worker, "zhangbin").start();

//        System.in.read();


        Thread.sleep(10000L);

    }
}
