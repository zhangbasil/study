package com.zhangbin.tool.singleton;

import java.io.IOException;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class SingletonTest {

    public static void main(String[] args) throws InterruptedException, IOException {
        for (int i = 0; i < 5000; i++) {
            new Thread(() -> {

                singleton1Instance();

            }).start();
        }

        System.in.read();

    }

    private static void singleton1Instance() {
        Singleton instance = Singleton.getInstance();
        System.out.println("instance = " + instance);
    }

}
